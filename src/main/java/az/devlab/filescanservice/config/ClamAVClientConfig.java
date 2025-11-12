package az.devlab.filescanservice.config;

import az.devlab.filescanservice.clientrest.ClamAVRestClient;
import az.devlab.filescanservice.clienttcp.ClamAVTcpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClamAVClientConfig {

    @Bean
    @ConditionalOnProperty(name = "clamav.mode", havingValue = "TCP", matchIfMissing = true)
    public ClamAVTcpClient clamAVTcpClient(ClamAVProperties p) {
        return new ClamAVTcpClient(p.getHost(), p.getPort(), p.getTimeoutMs());
    }

    @Bean
    @ConditionalOnProperty(name = "clamav.mode", havingValue = "REST")
    public ClamAVRestClient clamAVRestClient(ClamAVProperties p) {
        return new ClamAVRestClient(p.getRestUrl(), p.getTimeoutMs());
    }
}
