package it.gov.pagopa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gov.pagopa.dto.ErrorDTO;
import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.UserPermissionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Validated
@Tag(name = "PortalConsent", description = "")
@RequestMapping("${openapi.idPayWelfarePortalUserPermissions.base-path:/idpay/authorization}")
public interface PortalConsentController {

    @Operation(
            operationId = "getPortalConsent",
            summary = "get the portal consents",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortalConsentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "429", description = "Too Many Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Server ERROR", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    })
            }

    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/consent",
            produces = { "application/json" }
    )
    ResponseEntity<PortalConsentDTO> getPortalConsent(@RequestHeader(value = "Content-Language") String contentLanguage) throws JsonProcessingException;

    @Operation(
            operationId = "savePortalConsent",
            summary = "save the portal consent",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Authentication failed", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "429", description = "Too Many Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Server ERROR", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/consent",
            consumes = {"application/json"}
    )
     ResponseEntity<PortalConsentDTO> savePortalConsent(@RequestHeader(value = "Content-Language") String contentLanguage) throws JsonProcessingException;
}
