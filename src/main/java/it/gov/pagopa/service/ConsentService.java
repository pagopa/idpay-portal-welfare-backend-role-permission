package it.gov.pagopa.service;

import it.gov.pagopa.dto.ConsentDTO;
import it.gov.pagopa.dto.ConsentItemDTO;
import it.gov.pagopa.model.PortalConsent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsentService {
   ConsentDTO retrieveConsent(String userId, String acceptLanguage);

    void saveConsent(String userId, String acceptLanguage, List<ConsentItemDTO> body);
}
