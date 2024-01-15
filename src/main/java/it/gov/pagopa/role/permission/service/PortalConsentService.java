package it.gov.pagopa.role.permission.service;

import it.gov.pagopa.role.permission.dto.PortalConsentDTO;

public interface PortalConsentService {

    PortalConsentDTO get(String userId);

    void save(String userId, PortalConsentDTO consent);
}
