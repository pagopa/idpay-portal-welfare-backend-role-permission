package it.gov.pagopa.role.permission.exception;

import it.gov.pagopa.common.web.exception.ServiceException;
import it.gov.pagopa.common.web.exception.ServiceExceptionPayload;
import it.gov.pagopa.role.permission.constants.RolePermissionConstants;

public class PermissionNotFoundException extends ServiceException {

    public PermissionNotFoundException(String message) {
        this(RolePermissionConstants.ExceptionCode.PERMISSIONS_NOT_FOUND, message);
    }

    public PermissionNotFoundException(String code, String message) {
        this(code, message,null, false, null);
    }

    public PermissionNotFoundException(String code, String message, ServiceExceptionPayload response, boolean printStackTrace, Throwable ex) {
        super(code, message, response, printStackTrace, ex);
    }

}
