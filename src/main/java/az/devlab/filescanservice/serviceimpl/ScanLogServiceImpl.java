package az.devlab.filescanservice.serviceimpl;

import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.enums.Verdict;
import az.devlab.filescanservice.model.ScanLog;
import az.devlab.filescanservice.repository.ScanLogRepository;
import az.devlab.filescanservice.service.ScanLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ScanLogServiceImpl implements ScanLogService {

    private final ScanLogRepository repo;

    @Override
    public ScanLog start(String traceId, String filename, long sizeBytes) {
        ScanLog log = new ScanLog();
        log.setTraceId(traceId);
        log.setFilename(filename);
        log.setSizeBytes(sizeBytes);
        log.setStatus(ScanStatus.STARTED);
        log.setVerdict(Verdict.UNKNOWN);
        return repo.save(log);
    }


    @Override
    public void finish(String traceId, ScanStatus status, Verdict verdict, String message, long durationMs) {
        repo.findByTraceId(traceId).ifPresent(log -> {
            log.setStatus(status);
            log.setVerdict(verdict);
            log.setMessage(message);
            log.setDurationMs(durationMs);
            repo.save(log);
        });
    }
}
