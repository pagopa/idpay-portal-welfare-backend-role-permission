package it.gov.pagopa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ConsentException extends RuntimeException {

    private final int code;

    private final String message;

    private final HttpStatus httpStatus;
}
