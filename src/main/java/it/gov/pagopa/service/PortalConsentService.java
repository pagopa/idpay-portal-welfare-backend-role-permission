package it.gov.pagopa.service;

import it.gov.pagopa.dto.PortalConsentDTO;

import java.util.Optional;

public interface PortalConsentService {

    Optional<PortalConsentDTO> get(String userId);

    void save(String userId, PortalConsentDTO consent);
}
