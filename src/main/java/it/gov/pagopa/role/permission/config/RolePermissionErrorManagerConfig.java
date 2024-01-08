package it.gov.pagopa.role.permission.config;

import it.gov.pagopa.common.web.dto.ErrorDTO;
import it.gov.pagopa.role.permission.constants.RolePermissionConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolePermissionErrorManagerConfig {
    @Bean
    ErrorDTO defaultErrorDTO() {
        return new ErrorDTO(
                RolePermissionConstants.ExceptionCode.GENERIC_ERROR,
                "A generic error occurred"
        );
    }

    @Bean
    ErrorDTO tooManyRequestsErrorDTO() {
        return new ErrorDTO(RolePermissionConstants.ExceptionCode.TOO_MANY_REQUESTS, "Too Many Requests");
    }

    @Bean
    ErrorDTO templateValidationErrorDTO(){
        return new ErrorDTO(RolePermissionConstants.ExceptionCode.INVALID_REQUEST, null);
    }
}
