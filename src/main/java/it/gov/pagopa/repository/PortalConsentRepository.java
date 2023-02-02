package it.gov.pagopa.repository;

import it.gov.pagopa.model.PortalConsent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortalConsentRepository extends MongoRepository<PortalConsent, String> {
}
