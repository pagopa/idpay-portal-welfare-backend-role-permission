package it.gov.pagopa.common.mongo.retry;

import it.gov.pagopa.common.mongo.DummySpringRepository;
import it.gov.pagopa.common.mongo.config.MongoConfig;
import it.gov.pagopa.common.mongo.retry.exception.MongoRequestRateTooLargeRetryExpiredException;
import it.gov.pagopa.common.web.exception.ErrorManager;
import it.gov.pagopa.common.web.exception.MongoExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@TestPropertySource(
        properties = {
                "de.flapdoodle.mongodb.embedded.version=4.0.21",

                "spring.data.mongodb.database=idpay",
                "spring.data.mongodb.config.connectionPool.maxSize: 100",
                "spring.data.mongodb.config.connectionPool.minSize: 0",
                "spring.data.mongodb.config.connectionPool.maxWaitTimeMS: 120000",
                "spring.data.mongodb.config.connectionPool.maxConnectionLifeTimeMS: 0",
                "spring.data.mongodb.config.connectionPool.maxConnectionIdleTimeMS: 120000",
                "spring.data.mongodb.config.connectionPool.maxConnecting: 2",
        })
@ContextConfiguration(classes = {
        MongoRequestRateTooLargeAutomaticRetryAspect.class,
        ErrorManager.class,
        MongoExceptionHandler.class,
        MongoConfig.class,

        MongoRequestRateTooLargeRetryIntegrationTest.TestController.class,
        MongoRequestRateTooLargeRetryIntegrationTest.TestRepository.class,
})
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureDataMongo
class MongoRequestRateTooLargeRetryIntegrationTest {

    @Value("${mongo.request-rate-too-large.batch.max-retry:3}")
    private int maxRetry;
    @Value("${mongo.request-rate-too-large.batch.max-millis-elapsed:0}")
    private int maxMillisElapsed;

    private static final int API_RETRYABLE_MAX_RETRY = 5;

    @SpyBean
    private TestRepository testRepositorySpy;
    @Autowired
    private DummySpringRepository dummySpringRepository;

    @SpyBean
    private MongoRequestRateTooLargeAutomaticRetryAspect automaticRetryAspectSpy;

    private static int[] counter;

    @BeforeEach
    void init() {
        counter = new int[]{0};
    }

    @RestController
    @Slf4j
    static class TestController {

        @Autowired
        private TestRepository repository;

        @GetMapping("/test")
        String testEndpoint() {
            return buildNestedRepositoryMethodInvoke(repository);
        }

        @MongoRequestRateTooLargeApiRetryable(maxRetry = API_RETRYABLE_MAX_RETRY)
        @GetMapping("/test-api-retryable")
        String testEndpointRetryable() {
            return buildNestedRepositoryMethodInvoke(repository);
        }

        static String buildNestedRepositoryMethodInvoke(TestRepository repository) {
            return repository.test();
        }
    }

    @Service
    static class TestRepository {
        public String test() {
            counter[0]++;
            throw MongoRequestRateTooLargeRetryerTest.buildRequestRateTooLargeMongodbException_whenReading();

        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testController_Method() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isTooManyRequests())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"TOO_MANY_REQUESTS\"}", false));

        Assertions.assertEquals(1, counter[0]);
    }

    @Test
    void testControllerRetryable_Method() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test-api-retryable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isTooManyRequests())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"TOO_MANY_REQUESTS\"}", false));

        Assertions.assertEquals(counter[0], API_RETRYABLE_MAX_RETRY + 1);
    }

    @Test
    void testNoController_Method() {
        try {
            TestController.buildNestedRepositoryMethodInvoke(testRepositorySpy);
            Assertions.fail("Expected exception");
        } catch (MongoRequestRateTooLargeRetryExpiredException e) {
            Assertions.assertEquals(maxRetry + 1, e.getCounter());
            Assertions.assertEquals(maxRetry, e.getMaxRetry());
            Assertions.assertEquals(maxMillisElapsed, e.getMaxMillisElapsed());
            Assertions.assertTrue(e.getMillisElapsed() > 0);
        }

        Assertions.assertEquals(counter[0], maxRetry + 1);
    }

    @Test
    void testSpringRepositoryInterceptor() throws Throwable {
        // When
        dummySpringRepository.findByIdOrderById("ID");

        // Then
        Mockito.verify(automaticRetryAspectSpy).decorateRepositoryMethods(Mockito.argThat(i -> i.getArgs()[0].equals("ID")));
    }
}