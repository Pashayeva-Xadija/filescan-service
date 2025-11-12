package az.devlab.filescanservice.serviceimpl;

import az.devlab.filescanservice.config.StorageProperties;
import az.devlab.filescanservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageProperties props;

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        Path dir = Path.of(props.getUploadDir());
        Files.createDirectories(dir);
        Path target = dir.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }

    @Override
    public String moveToQuarantine(String storedPath) throws IOException {
        Path qdir = Path.of(props.getQuarantineDir());
        Files.createDirectories(qdir);
        Path src = Path.of(storedPath);
        Path dst = qdir.resolve(src.getFileName());
        Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
        return dst.toString();
    }
}
