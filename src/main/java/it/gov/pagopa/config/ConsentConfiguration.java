package it.gov.pagopa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "consent-config")
@Component
@Data
public class ConsentConfiguration {

    private String githubCommitsUri;

    private String defaultLocale;

    private List<String> allowedLocals;

    private Map<String, Map<String, String>> consentMap = new HashMap<>();

}