package az.devlab.filescanservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "clamav")
public class ClamAVProperties {

    private String mode = "TCP";
    private String host = "localhost";
    private int port = 3310;
    private int timeoutMs = 5000;
    private String restUrl;
}
