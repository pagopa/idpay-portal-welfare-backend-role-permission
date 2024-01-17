package it.gov.pagopa.role.permission.repository;

import it.gov.pagopa.role.permission.model.PortalConsent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortalConsentRepository extends MongoRepository<PortalConsent, String> {
}
