package it.gov.pagopa.service;

import it.gov.pagopa.dto.ConsentDTO;
import it.gov.pagopa.model.PortalConsent;
import org.springframework.stereotype.Service;

@Service
public interface ConsentService {
   ConsentDTO retrieveConsent(String userId, String acceptLanguage);
}
