package it.gov.pagopa.service;

import it.gov.pagopa.dto.PortalConsentDTO;
import org.springframework.stereotype.Service;

@Service
public interface PortalConsentService {

    void save(PortalConsentDTO consent);
}
