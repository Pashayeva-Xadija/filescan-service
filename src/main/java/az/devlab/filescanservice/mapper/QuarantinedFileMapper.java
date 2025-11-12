package az.devlab.filescanservice.mapper;

import az.devlab.filescanservice.dto.QuarantinedFileResponse;
import az.devlab.filescanservice.model.QuarantinedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface QuarantinedFileMapper {

    @Mapping(target = "traceId",      source = "traceId")
    @Mapping(target = "originalName", source = "originalName")
    @Mapping(target = "storedPath",   source = "storedPath")
    @Mapping(target = "checksum",     source = "checksum")
    @Mapping(target = "reason",       source = "reason")
    @Mapping(target = "createdAt",    source = "createdAt")
    QuarantinedFileResponse toResponse(QuarantinedFile entity);
}
