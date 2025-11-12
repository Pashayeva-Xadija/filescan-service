package az.devlab.filescanservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadRequest {
    @NotNull(message = "file boş ola bilməz")
    private MultipartFile file;
}

