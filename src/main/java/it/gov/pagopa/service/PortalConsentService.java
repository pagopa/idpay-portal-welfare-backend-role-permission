package it.gov.pagopa.service;

import it.gov.pagopa.dto.PortalConsentDTO;
import org.springframework.stereotype.Service;

@Service
public interface PortalConsentService {

    PortalConsentDTO get(String userId);

    void save(String userId, PortalConsentDTO consent);
}
