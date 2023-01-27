package it.gov.pagopa.controller.consent;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.service.PortalConsentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class PortalConsentControllerImpl implements PortalConsentController {

    private final PortalConsentService portalConsentService;

    public PortalConsentControllerImpl(PortalConsentService portalConsentService) {
        this.portalConsentService = portalConsentService;
    }

    @Override
    public ResponseEntity<PortalConsentDTO> getPortalConsent(String uid) {
        Optional<PortalConsentDTO> consentOptional = portalConsentService.get(uid);

        return consentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    @Override
    public void savePortalConsent(String uid, PortalConsentDTO consent) {
        portalConsentService.save(uid, consent);
    }
}
