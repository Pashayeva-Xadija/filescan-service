package az.devlab.filescanservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String uploadDir = "/data/uploads";
    private String quarantineDir = "/data/quarantine";
}
