package az.devlab.filescanservice.mapper;

import az.devlab.filescanservice.dto.QuarantinedFileResponse;
import az.devlab.filescanservice.model.QuarantinedFile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface QuarantinedFileMapper {

    QuarantinedFileResponse toResponse(QuarantinedFile entity);
}