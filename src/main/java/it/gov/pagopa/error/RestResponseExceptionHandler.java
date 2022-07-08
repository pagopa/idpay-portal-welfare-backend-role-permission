package it.gov.pagopa.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
//@Slf4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    // API

    // 400
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        final String bodyOfResponse = "Data Integrity Violation";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String bodyOfResponse = "Http Message Not Readable";
        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String bodyOfResponse = "Method Argument Not Valid";
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }


    // 404
//  @ExceptionHandler(value = { EntityNotFoundException.class, MyResourceNotFoundException.class })
//  protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
//    final String bodyOfResponse = "This should be application specific";
//    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//  }

    // 409
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
    public ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Data access Conflict";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 412

    // 500
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
//        logger.error("500 Status Code", ex);
        final String bodyOfResponse = "Internal server Error";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
