package az.devlab.filescanservice.serviceimpl;

import az.devlab.filescanservice.clientrest.ClamAVRestClient;
import az.devlab.filescanservice.clienttcp.ClamAVTcpClient;
import az.devlab.filescanservice.config.ClamAVProperties;
import az.devlab.filescanservice.dto.UploadResponse;
import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.enums.Verdict;
import az.devlab.filescanservice.exception.ScanFailedException;
import az.devlab.filescanservice.mapper.UploadMapper;
import az.devlab.filescanservice.model.ScanLog;
import az.devlab.filescanservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileScanServiceImpl implements FileScanService {

    private final StorageService storageService;
    private final ScanLogService logService;
    private final QuarantineService quarantineService;
    private final UploadMapper uploadMapper;
    private final ClamAVProperties clamProps;
    private final Optional<ClamAVTcpClient> tcpClient;
    private final Optional<ClamAVRestClient> restClient;
    private final Optional<EmailNotificationService> emailService;

    @Override
    public UploadResponse uploadAndScan(MultipartFile file) {
        final String traceId = UUID.randomUUID().toString();
        final String filename = file.getOriginalFilename();
        final long size = file.getSize();

        ScanLog logEntity = logService.start(traceId, filename, size);


        final String storedPath;
        try {
            storedPath = storageService.storeFile(file);
        } catch (Exception e) {
            logService.finish(traceId, ScanStatus.FAILED, Verdict.UNKNOWN, "Storage error: " + e.getMessage(), 0);
            throw new ScanFailedException("Failed to store file: " + e.getMessage());
        }

        doScanAsync(traceId, filename, storedPath);

        return uploadMapper.started(traceId, filename, size);
    }

    @Async("scanExecutor")
    public void doScanAsync(String traceId, String filename, String storedPath) {
        final Instant t0 = Instant.now();
        try {
            byte[] data = Files.readAllBytes(Path.of(storedPath));
            String raw = scanWithActiveClient(data);
            boolean clean = raw.contains("OK") && !raw.contains("FOUND");
            long duration = Duration.between(t0, Instant.now()).toMillis();

            if (clean) {
                logService.finish(traceId, ScanStatus.CLEAN, Verdict.CLEAN, raw, duration);
                log.info("CLEAN: {} ({})", filename, traceId);
            } else {
                String qPath = storageService.moveToQuarantine(storedPath);
                quarantineService.onQuarantined(qPath);
                logService.finish(traceId, ScanStatus.INFECTED, Verdict.INFECTED, raw, duration);
                log.warn("INFECTED: {} → {}", filename, qPath);
                emailService.ifPresent(s -> s.notifyInfection(traceId, filename, raw, qPath));
            }
        } catch (Exception e) {
            long duration = Duration.between(t0, Instant.now()).toMillis();
            logService.finish(traceId, ScanStatus.FAILED, Verdict.UNKNOWN, "Scan error: " + e.getMessage(), duration);
            log.error("FAILED: {} ({}) {}", filename, traceId, e.getMessage());
            emailService.ifPresent(s -> s.notifyFailure(traceId, filename, e.getMessage()));
        }
    }


    private String scanWithActiveClient(byte[] data) throws java.io.IOException {
        String mode = clamProps.getMode();
        if ("TCP".equalsIgnoreCase(mode) && tcpClient.isPresent()) {
            return tcpClient.get().scan(data);
        }
        if ("REST".equalsIgnoreCase(mode) && restClient.isPresent()) {
            return restClient.get().scan(data);
        }
        throw new ScanFailedException("No active ClamAV client (mode=" + mode + ")");
    }

}
