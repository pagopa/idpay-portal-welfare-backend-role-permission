package it.gov.pagopa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorizationPermissionException extends RuntimeException {
    private final int code;
    private final String message;
}