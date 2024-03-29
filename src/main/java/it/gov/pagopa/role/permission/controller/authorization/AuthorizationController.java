package it.gov.pagopa.role.permission.controller.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gov.pagopa.common.web.dto.ErrorDTO;
import it.gov.pagopa.role.permission.dto.UserPermissionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IdPay - Authorization Interface to retrieve functionalities's permissions
 */

@Validated
@Tag(name = "UserPermission", description = "")
@RequestMapping("${openapi.idPayWelfarePortalUserPermissions.base-path:/idpay/authorization}")
public interface AuthorizationController {

    /**
     * GET /permissions : Return User Permissions
     *
     * @return Check successful (status code 200)
     *         or Bad Request (status code 400)
     *         or The requested ID was not found (status code 404)
     *         or Server ERROR (status code 500)
     */
    @Operation(
            operationId = "userPermission",
            summary = "Return User Permissions",
            tags = { "UserPermission" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Check successful", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserPermissionDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "The requested ID was not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Server ERROR", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @GetMapping(
            value = "/permissions/{role}",
            produces = { "application/json" }
    )
    ResponseEntity<UserPermissionDTO> getUserPermissions(
            @Parameter(name = "role", description = "Role Type (eg. admin & ope_base)", required = true)
            @PathVariable("role") String role
    ) throws JsonProcessingException;

}



