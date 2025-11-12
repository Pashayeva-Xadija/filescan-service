package az.devlab.filescanservice.controller;

import az.devlab.filescanservice.dto.UploadResponse;
import az.devlab.filescanservice.service.FileScanService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileScanService fileScanService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> upload(@RequestParam("file") @NotNull MultipartFile file) {
        UploadResponse response = fileScanService.uploadAndScan(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
