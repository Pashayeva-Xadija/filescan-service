package az.devlab.filescanservice.service;

import az.devlab.filescanservice.model.ScanLog;
import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.enums.Verdict;

public interface ScanLogService {
    ScanLog start(String traceId, String filename, long sizeBytes);
    void finish(String traceId, ScanStatus status, Verdict verdict, String message, long durationMs);
}
