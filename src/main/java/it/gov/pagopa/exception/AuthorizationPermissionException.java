package it.gov.pagopa.exception;

import it.gov.pagopa.common.web.exception.ClientExceptionWithBody;
import lombok.Getter;
import org.springframework.http.HttpStatus;

    @Getter
    @SuppressWarnings("squid:S110")
public class AuthorizationPermissionException extends ClientExceptionWithBody {

    public AuthorizationPermissionException(Integer code, String message){
    super(HttpStatus.valueOf(code), code, message);}
}