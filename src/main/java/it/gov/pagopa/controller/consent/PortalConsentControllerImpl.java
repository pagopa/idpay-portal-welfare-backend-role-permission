package it.gov.pagopa.controller.consent;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.service.PortalConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("/idpay/portal/consent")
public class PortalConsentControllerImpl implements PortalConsentController {

    @Autowired
    PortalConsentService portalConsentService;

    @Value("${portal.welfare.tos.privacy}")
    private String linkPrivacy;

    @Value("${portal.welfare.tos.tc}")
    private String linkTc;

    @Override
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PortalConsentDTO> getPortalConsent(String userId) throws JsonProcessingException {
        PortalConsentDTO consentDTO = portalConsentService.get(userId);

        return consentDTO != null
                ? ResponseEntity.ok(consentDTO)
                : ResponseEntity.notFound().build();
    }

    @Override
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> savePortalConsent(String userId, PortalConsentDTO consent) throws JsonProcessingException{
        portalConsentService.save(userId, consent);
        return ResponseEntity.ok().build();
    }
}
