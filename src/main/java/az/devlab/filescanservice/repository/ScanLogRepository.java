package az.devlab.filescanservice.repository;

import az.devlab.filescanservice.model.ScanLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScanLogRepository extends JpaRepository<ScanLog, Long> {
    Optional<ScanLog> findTopByTraceIdOrderByCreatedAtDesc(String traceId);
    boolean existsByTraceId(String traceId);
    Optional<ScanLog> findByTraceId(String traceId);
}
