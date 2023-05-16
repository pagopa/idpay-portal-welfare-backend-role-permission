package it.gov.pagopa.controller.consent;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.gov.pagopa.dto.ErrorDTO;
import it.gov.pagopa.dto.PortalConsentDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/idpay")
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
                    })
            }

    )
    @GetMapping(
            value = "/consent",
            produces = { "application/json" }
    )
    PortalConsentDTO getPortalConsent(@RequestParam(value = "userId") String userId) throws JsonProcessingException;

    @Operation(
            operationId = "savePortalConsent",
            summary = "save the portal consent",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Authentication failed", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Server ERROR", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    })
            }
    )
    @PostMapping(
            value = "/consent",
            consumes = {"application/json"}
    )
    void savePortalConsent(@RequestParam(value = "userId") String userId, @RequestBody PortalConsentDTO consent) throws JsonProcessingException;
}
