package it.gov.pagopa.common.web.exception;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import it.gov.pagopa.common.utils.MemoryAppender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {
        ErrorManagerTest.TestController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {ErrorManagerTest.TestController.class, ErrorManager.class})
class ErrorManagerTest {
  private static final String EXPECTED_GENERIC_ERROR = "{\"code\":\"Error\",\"message\":\"Something gone wrong\"}";

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private TestController testControllerSpy;
  private static MemoryAppender memoryAppender;

  @RestController
  @Slf4j
  static class TestController {

    @GetMapping("/test")
    String testEndpoint() {
      return "OK";
    }
  }

  @BeforeAll
  static void configureMemoryAppender(){
    ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ErrorManager.class.getName());
    memoryAppender = new MemoryAppender();
    memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
    logger.setLevel(ch.qos.logback.classic.Level.INFO);
    logger.addAppender(memoryAppender);
    memoryAppender.start();
  }

  @BeforeEach
  void clearMemoryAppender(){
    memoryAppender.reset();
  }

  @Test
  void handleExceptionClientExceptionNoBody() throws Exception {
    Mockito.doThrow(
                    new ClientExceptionNoBody(HttpStatus.BAD_REQUEST, "NOTFOUND ClientExceptionNoBody"))
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    checkStackTraceSuppressedLog(memoryAppender,
            "A ClientExceptionNoBody occurred handling request GET /test: HttpStatus 400 BAD_REQUEST - NOTFOUND ClientExceptionNoBody at it.gov.pagopa.common.web.exception.ErrorManagerTest\\$TestController.testEndpoint\\(ErrorManagerTest.java:[0-9]+\\)");

  }

  @Test
  void handleExceptionClientExceptionWithBody() throws Exception {
    Mockito.doThrow(
                    new ClientExceptionWithBody(HttpStatus.BAD_REQUEST, "Error", "Error ClientExceptionWithBody"))
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content()
                    .json("{\"code\":\"Error\",\"message\":\"Error ClientExceptionWithBody\"}"));

    Mockito.doThrow(
                    new ClientExceptionWithBody(HttpStatus.BAD_REQUEST, "Error", "Error ClientExceptionWithBody",
                            new Exception()))
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content()
                    .json("{\"code\":\"Error\",\"message\":\"Error ClientExceptionWithBody\"}"));
  }

  @Test
  void handleExceptionClientExceptionTest() throws Exception {

    Mockito.doThrow(ClientException.class)
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content()
                    .json(EXPECTED_GENERIC_ERROR));

    checkStackTraceSuppressedLog(memoryAppender, "A ClientException occurred handling request GET /test: HttpStatus null - null at UNKNOWN");
    memoryAppender.reset();

    Mockito.doThrow(
                    new ClientException(HttpStatus.BAD_REQUEST, "ClientException with httpStatus and message"))
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content()
                    .json(EXPECTED_GENERIC_ERROR));

    checkStackTraceSuppressedLog(memoryAppender, "A ClientException occurred handling request GET /test: HttpStatus 400 BAD_REQUEST - ClientException with httpStatus and message at it.gov.pagopa.common.web.exception.ErrorManagerTest\\$TestController.testEndpoint\\(ErrorManagerTest.java:[0-9]+\\)");
    memoryAppender.reset();


    Mockito.doThrow(new ClientException(HttpStatus.BAD_REQUEST,
                    "ClientException with httpStatus, message and throwable", new Throwable()))
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content()
                    .json(EXPECTED_GENERIC_ERROR));

    checkLog(memoryAppender,
            "Something went wrong handling request GET /test: HttpStatus 400 BAD_REQUEST - ClientException with httpStatus, message and throwable",
            "it.gov.pagopa.common.web.exception.ClientException: ClientException with httpStatus, message and throwable",
            "it.gov.pagopa.common.web.exception.ErrorManagerTest$TestController.testEndpoint"
    );
  }

  @Test
  void handleExceptionRuntimeException() throws Exception {
    Mockito.doThrow(RuntimeException.class)
            .when(testControllerSpy).testEndpoint();

    mockMvc.perform(MockMvcRequestBuilders.get("/test")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content()
                    .json(EXPECTED_GENERIC_ERROR));
  }

  public static void checkStackTraceSuppressedLog(MemoryAppender memoryAppender, String expectedLoggedMessage) {
    String loggedMessage = memoryAppender.getLoggedEvents().get(0).getFormattedMessage();
    Assertions.assertTrue(Pattern.matches(expectedLoggedMessage, loggedMessage),
            "Unexpected logged message: " + loggedMessage);
  }

  public static void checkLog(MemoryAppender memoryAppender, String expectedLoggedMessageRegexp, String expectedLoggedExceptionMessage, String expectedLoggedExceptionOccurrencePosition) {
    ILoggingEvent loggedEvent = memoryAppender.getLoggedEvents().get(0);
    IThrowableProxy loggedException = loggedEvent.getThrowableProxy();
    StackTraceElementProxy loggedExceptionOccurrenceStackTrace = loggedException.getStackTraceElementProxyArray()[0];

    String loggedMessage = loggedEvent.getFormattedMessage();
    Assertions.assertTrue(Pattern.matches(expectedLoggedMessageRegexp,
                    loggedEvent.getFormattedMessage()),
            "Unexpected logged message: " + loggedMessage);

    Assertions.assertEquals(expectedLoggedExceptionMessage,
            loggedException.getClassName() + ": " + loggedException.getMessage());

    Assertions.assertEquals(expectedLoggedExceptionOccurrencePosition,
            loggedExceptionOccurrenceStackTrace.getStackTraceElement().getClassName() + "." + loggedExceptionOccurrenceStackTrace.getStackTraceElement().getMethodName());
  }
}
