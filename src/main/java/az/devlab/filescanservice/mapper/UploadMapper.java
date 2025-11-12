package az.devlab.filescanservice.mapper;

import az.devlab.filescanservice.dto.UploadResponse;
import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.model.ScanLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UploadMapper {

    default UploadResponse started(String traceId, String filename, long sizeBytes) {
        return UploadResponse.builder()
                .traceId(traceId)
                .filename(filename)
                .sizeBytes(sizeBytes)
                .status(ScanStatus.STARTED)
                .build();
    }

    @Mapping(target = "storedPath", expression = "java(storedPath)")
    UploadResponse finished(ScanLog log, String storedPath);
}

