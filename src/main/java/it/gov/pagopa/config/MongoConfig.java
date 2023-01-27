package it.gov.pagopa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("it.gov.pagopa.repository")
public class MongoConfig {
}