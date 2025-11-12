package az.devlab.filescanservice.mapper;

import az.devlab.filescanservice.dto.PageResponse;
import az.devlab.filescanservice.dto.QuarantinedFileResponse;
import az.devlab.filescanservice.dto.ScanLogItem;
import az.devlab.filescanservice.model.QuarantinedFile;
import az.devlab.filescanservice.model.ScanLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class PageMapper {

    private final ScanLogMapper scanLogMapper;
    private final QuarantinedFileMapper quarantinedFileMapper;


    public PageResponse<ScanLogItem> toScanLogPage(Page<ScanLog> page) {
        List<ScanLogItem> content = page.getContent().stream()
                .map(scanLogMapper::toItem)
                .toList();

        return buildPageResponse(page, content);
    }

    public PageResponse<QuarantinedFileResponse> toQuarantinePage(Page<QuarantinedFile> page) {
        List<QuarantinedFileResponse> content = page.getContent().stream()
                .map(quarantinedFileMapper::toResponse)
                .toList();

        return buildPageResponse(page, content);
    }

    private static <T> PageResponse<T> buildPageResponse(Page<?> page, List<T> content) {
        return PageResponse.<T>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
