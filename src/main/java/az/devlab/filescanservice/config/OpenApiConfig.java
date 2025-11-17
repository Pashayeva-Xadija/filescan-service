package az.devlab.filescanservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String API_KEY_SCHEME = "ApiKeyAuth";
    private static final String API_KEY_HEADER = "X-API-KEY";

    @Bean
    public OpenAPI fileScanOpenAPI() {

        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(API_KEY_HEADER)
                .description("Provide API key in header: " + API_KEY_HEADER);

        return new OpenAPI()
                .info(new Info()
                        .title("FileScan Service API")
                        .description("Spring Boot & ClamAV based file virus scanning service (async scan, quarantine, logging).")
                        .version("v1")
                        .contact(new Contact()
                                .name("Xadija Pashayeva")
                                .email("xadijapashayeva@gmail.com"))
                        .license(new License().name("Apache-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:2122").description("Local"),
                        new Server().url("https://your-domain.com").description("Production")
                ))
                .schemaRequirement(API_KEY_SCHEME, apiKey)
                .addSecurityItem(new SecurityRequirement().addList(API_KEY_SCHEME));
    }

    @Bean
    public GroupedOpenApi filesApi() {
        return GroupedOpenApi.builder()
                .group("files")
                .pathsToMatch("/api/files/**")
                .build();
    }

    @Bean
    public GroupedOpenApi logsApi() {
        return GroupedOpenApi.builder()
                .group("logs")
                .pathsToMatch("/api/logs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi quarantineApi() {
        return GroupedOpenApi.builder()
                .group("quarantine")
                .pathsToMatch("/api/quarantine/**")
                .build();
    }
}
