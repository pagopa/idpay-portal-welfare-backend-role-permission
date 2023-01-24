package it.gov.pagopa.config;

import it.gov.pagopa.connector.onetrust.OneTrustRestClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        OneTrustRestClient.class
})
public class RestConnectorConfig {
}
