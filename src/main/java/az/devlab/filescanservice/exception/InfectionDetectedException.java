package az.devlab.filescanservice.exception;

import lombok.Getter;


@Getter
public class InfectionDetectedException extends RuntimeException {
    private final String traceId;
    private final String reason;

    public InfectionDetectedException(String traceId, String reason) {
        super("Infected file detected: " + reason);
        this.traceId = traceId;
        this.reason = reason;
    }
}
