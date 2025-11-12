package az.devlab.filescanservice.service;

public interface EmailNotificationService {
    void notifyInfection(String traceId, String filename, String reason, String quarantinedPath);
    default void notifyFailure(String traceId, String filename, String errorMessage) {}
}
