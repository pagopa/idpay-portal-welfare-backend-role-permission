package it.gov.pagopa.service;

import it.gov.pagopa.dto.PortalConsentDTO;

public interface PortalConsentService {

    PortalConsentDTO get(String userId);

    void save(String userId, PortalConsentDTO consent);
}
