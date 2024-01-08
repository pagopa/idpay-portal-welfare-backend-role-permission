package it.gov.pagopa.role.permission.config;

import it.gov.pagopa.role.permission.onetrust.OneTrustRestClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        OneTrustRestClient.class
})
public class RestConnectorConfig {
}
