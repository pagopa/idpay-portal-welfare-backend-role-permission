package it.gov.pagopa.role.permission.exception;

import it.gov.pagopa.common.web.exception.ServiceException;
import it.gov.pagopa.common.web.exception.ServiceExceptionPayload;
import it.gov.pagopa.role.permission.constants.RolePermissionConstants;

public class VersionNotMatchedException extends ServiceException {

    public VersionNotMatchedException(String message) {
        this(RolePermissionConstants.ExceptionCode.VERSION_NOT_MATCHED, message);
    }

    public VersionNotMatchedException(String code, String message) {
        this(code, message,null, false, null);
    }

    public VersionNotMatchedException(String code, String message, ServiceExceptionPayload response, boolean printStackTrace, Throwable ex) {
        super(code, message, response, printStackTrace, ex);
    }

}
