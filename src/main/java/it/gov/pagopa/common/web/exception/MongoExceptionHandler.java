package it.gov.pagopa.common.web.exception;

import it.gov.pagopa.common.mongo.retry.MongoRequestRateTooLargeRetryer;
import it.gov.pagopa.common.mongo.retry.exception.MongoRequestRateTooLargeRetryExpiredException;
import it.gov.pagopa.common.web.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MongoExceptionHandler {

  @Autowired
  private ErrorManager errorManager;

  @ExceptionHandler(DataAccessException.class)
  protected ResponseEntity<ErrorDTO> handleDataAccessException(
      DataAccessException ex, HttpServletRequest request) {

    if (MongoRequestRateTooLargeRetryer.isRequestRateTooLargeException(ex)) {
      Long retryAfterMs = MongoRequestRateTooLargeRetryer.getRetryAfterMs(ex);

      return getErrorDTOResponseEntity(ex, request, retryAfterMs);
    } else {
      return errorManager.handleException(ex, request);
    }
  }

  @ExceptionHandler(MongoRequestRateTooLargeRetryExpiredException.class)
  protected ResponseEntity<ErrorDTO> handleMongoRequestRateTooLargeRetryExpiredException(
      MongoRequestRateTooLargeRetryExpiredException ex, HttpServletRequest request) {

    return getErrorDTOResponseEntity(ex, request, ex.getRetryAfterMs());
  }

  @NotNull
  private ResponseEntity<ErrorDTO> getErrorDTOResponseEntity(Exception ex,
      HttpServletRequest request, Long retryAfterMs) {
    String message = ex.getMessage();

    log.info(
        "A MongoQueryException (RequestRateTooLarge) occurred handling request {}: HttpStatus 429 - {}",
        ErrorManager.getRequestDetails(request), message);
    log.debug("Something went wrong while accessing MongoDB", ex);

    final BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .contentType(MediaType.APPLICATION_JSON);

    if (retryAfterMs != null) {
      long retryAfter = (long) Math.ceil((double) retryAfterMs / 1000);
      bodyBuilder.header(HttpHeaders.RETRY_AFTER, String.valueOf(retryAfter))
          .header("Retry-After-Ms", String.valueOf(retryAfterMs));
    }

    return bodyBuilder
        .body(new ErrorDTO(HttpStatus.TOO_MANY_REQUESTS.value(), "TOO_MANY_REQUESTS"));
  }

}
