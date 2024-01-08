package it.gov.pagopa.role.permission.constants;

public final class RolePermissionConstants {
    public static final String PERMISSIONS_NOT_FOUND_MSG = "Permissions not found for [%s] role.";

    private RolePermissionConstants(){}

    public static final class ExceptionCode {
        public static final String PERMISSIONS_NOT_FOUND = "ROLE_PERMISSIONS_NOT_FOUND";
        public static final String VERSION_NOT_MATCHED = "ROLE_PERMISSION_VERSION_NOT_MATCHED";
        public static final String GENERIC_ERROR = "ROLE_PERMISSION_GENERIC_ERROR";
        public static final String TOO_MANY_REQUESTS = "ROLE_PERMISSION_TOO_MANY_REQUESTS";
        public static final String INVALID_REQUEST = "ROLE_PERMISSION_INVALID_REQUEST";

        private ExceptionCode() {}
    }
}
