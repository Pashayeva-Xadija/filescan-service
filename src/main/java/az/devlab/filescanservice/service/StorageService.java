package az.devlab.filescanservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String storeFile(MultipartFile file) throws IOException;
    String moveToQuarantine(String storedPath) throws IOException;
}
