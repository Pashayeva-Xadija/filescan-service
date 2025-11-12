package az.devlab.filescanservice.service;

import az.devlab.filescanservice.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileScanService {
    UploadResponse uploadAndScan(MultipartFile file);
}
