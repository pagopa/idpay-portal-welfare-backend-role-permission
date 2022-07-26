package it.gov.pagopa.repository;

import it.gov.pagopa.model.PortalConsent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends MongoRepository<PortalConsent, String> {
}
