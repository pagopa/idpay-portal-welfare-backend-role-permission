package it.gov.pagopa.role.permission.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("IDPAY-WelfarePortalUserPermissions_v1")
                    .description("Description TBD")
                    .version("1.0")
            )
        ;
    }
}