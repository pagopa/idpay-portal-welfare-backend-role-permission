package it.gov.pagopa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.service.PortalConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;


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
    public ResponseEntity<PortalConsentDTO> getPortalConsent(@RequestHeader(value = "Content-Language") String contentLanguage) throws JsonProcessingException {
        return null;
    }

    @Override
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PortalConsentDTO> savePortalConsent(@RequestHeader(value = "Content-Language") String contentLanguage) throws JsonProcessingException{
        return null;
    }
}
