package az.devlab.filescanservice.controller;

import az.devlab.filescanservice.dto.PageResponse;
import az.devlab.filescanservice.dto.QuarantinedFileResponse;
import az.devlab.filescanservice.mapper.PageMapper;
import az.devlab.filescanservice.model.QuarantinedFile;
import az.devlab.filescanservice.repository.QuarantinedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quarantine")
@RequiredArgsConstructor
public class QuarantineController {

    private final QuarantinedFileRepository quarantinedFileRepository;
    private final PageMapper pageMapper;

    @GetMapping
    public ResponseEntity<PageResponse<QuarantinedFileResponse>> list(@PageableDefault(size = 20) Pageable pageable) {
        Page<QuarantinedFile> page = quarantinedFileRepository.findAll(pageable);
        PageResponse<QuarantinedFileResponse> response = pageMapper.toQuarantinePage(page);
        return ResponseEntity.ok(response);
    }
}
