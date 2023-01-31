package it.gov.pagopa.controller.consent;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.service.PortalConsentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PortalConsentControllerImpl implements PortalConsentController {

    private final PortalConsentService portalConsentService;

    public PortalConsentControllerImpl(PortalConsentService portalConsentService) {
        this.portalConsentService = portalConsentService;
    }

    @Override
    public ResponseEntity<PortalConsentDTO> getPortalConsent(String userId) {
        return ResponseEntity.ok(
                portalConsentService.get(userId)
        );
    }

    @Override
    public void savePortalConsent(String userId, PortalConsentDTO consent) {
        portalConsentService.save(userId, consent);
    }
}
