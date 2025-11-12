package az.devlab.filescanservice.controller;

import az.devlab.filescanservice.dto.PageResponse;
import az.devlab.filescanservice.dto.ScanLogItem;
import az.devlab.filescanservice.mapper.PageMapper;
import az.devlab.filescanservice.model.ScanLog;
import az.devlab.filescanservice.repository.ScanLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class ScanLogController {

    private final ScanLogRepository scanLogRepository;
    private final PageMapper pageMapper;

    @GetMapping
    public ResponseEntity<PageResponse<ScanLogItem>> list(@PageableDefault(size = 20) Pageable pageable) {
        Page<ScanLog> page = scanLogRepository.findAll(pageable);
        PageResponse<ScanLogItem> response = pageMapper.toScanLogPage(page);
        return ResponseEntity.ok(response);
    }
}
