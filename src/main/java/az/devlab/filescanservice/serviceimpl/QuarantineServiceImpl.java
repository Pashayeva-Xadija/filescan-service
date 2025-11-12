package az.devlab.filescanservice.serviceimpl;

import az.devlab.filescanservice.service.QuarantineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuarantineServiceImpl implements QuarantineService {
    @Override
    public void onQuarantined(String quarantinedPath) {
        log.warn("File moved to quarantine: {}", quarantinedPath);
    }
}
