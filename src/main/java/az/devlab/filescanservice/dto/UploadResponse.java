package az.devlab.filescanservice.dto;

import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.enums.Verdict;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadResponse {
    private final String traceId;
    private final String filename;
    private final Long sizeBytes;
    private final ScanStatus status;
    private final Verdict verdict;
    private final String storedPath;
    private final String message;
}
