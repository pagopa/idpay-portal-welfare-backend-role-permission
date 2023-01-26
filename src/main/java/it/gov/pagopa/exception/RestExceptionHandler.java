package it.gov.pagopa.exception;

import it.gov.pagopa.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(ConsentException.class)
    public ResponseEntity<ErrorDTO> handleConsentException(ConsentException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorDTO(
                ex.getCode(),
                ex.getMessage()),
                ex.getHttpStatus());
    }
}
