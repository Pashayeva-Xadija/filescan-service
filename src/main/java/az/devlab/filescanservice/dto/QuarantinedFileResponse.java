package az.devlab.filescanservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class QuarantinedFileResponse {
    private final String traceId;
    private final String originalName;
    private final String storedPath;
    private final String checksum;
    private final String reason;
    private final Instant createdAt;
}
