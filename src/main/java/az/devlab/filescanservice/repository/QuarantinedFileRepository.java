package az.devlab.filescanservice.repository;

import az.devlab.filescanservice.model.QuarantinedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuarantinedFileRepository extends JpaRepository<QuarantinedFile, Long> {
    Optional<QuarantinedFile> findByTraceId(String traceId);
}
