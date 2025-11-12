package az.devlab.filescanservice.dto;

import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.enums.Verdict;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScanResultResponse {
    private final String traceId;
    private final String filename;
    private final Long sizeBytes;
    private final ScanStatus status;
    private final Verdict verdict;
    private final String message;
    private final Long durationMs;
    private final Instant createdAt;
}