package az.devlab.filescanservice.mapper;

import az.devlab.filescanservice.dto.ScanLogItem;
import az.devlab.filescanservice.dto.ScanResultResponse;
import az.devlab.filescanservice.enums.ScanStatus;
import az.devlab.filescanservice.model.ScanLog;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ScanLogMapper {

    @Mapping(target = "id",         source = "id")
    @Mapping(target = "traceId",    source = "traceId")
    @Mapping(target = "filename",   source = "filename")
    @Mapping(target = "sizeBytes",  source = "sizeBytes")
    @Mapping(target = "verdict",    source = "verdict")
    @Mapping(target = "status",     source = "status")
    @Mapping(target = "message",    source = "message")
    @Mapping(target = "durationMs", source = "durationMs")
    @Mapping(target = "createdAt",  source = "createdAt")
    ScanLogItem toItem(ScanLog entity);


    @Mapping(target = "traceId",    source = "traceId")
    @Mapping(target = "filename",   source = "filename")
    @Mapping(target = "sizeBytes",  source = "sizeBytes")
    @Mapping(target = "status",     source = "status")
    @Mapping(target = "verdict",    source = "verdict")
    @Mapping(target = "message",    source = "message")
    @Mapping(target = "durationMs", source = "durationMs")
    @Mapping(target = "createdAt",  source = "createdAt")
    ScanResultResponse toResult(ScanLog entity);


    @AfterMapping
    default void ensureStatus(@MappingTarget ScanResultResponse.ScanResultResponseBuilder builder, ScanLog entity) {
        if (entity != null && entity.getStatus() == null) {
            builder.status(ScanStatus.STARTED);
        }
    }
}
