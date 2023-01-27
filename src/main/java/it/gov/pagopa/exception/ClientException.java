package it.gov.pagopa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ClientException extends RuntimeException {

    private final int code;

    private final String message;

    private final HttpStatus httpStatus;

    public ClientException(HttpStatus httpStatus) {
        this(httpStatus.value(), "Something went wrong", httpStatus);
    }
}
