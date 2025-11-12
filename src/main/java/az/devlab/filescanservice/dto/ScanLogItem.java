package az.devlab.filescanservice.dto;

import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.enums.Verdict;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ScanLogItem {
    private final Long id;
    private final String traceId;
    private final String filename;
    private final Verdict verdict;
    private final ScanStatus status;
    private final String message;
    private final Long sizeBytes;
    private final Long durationMs;
    private final Instant createdAt;
}