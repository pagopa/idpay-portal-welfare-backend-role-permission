package it.gov.pagopa.repository;

import it.gov.pagopa.dto.PortalConsentDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortalConsentRepository extends MongoRepository<PortalConsentDTO, String> {
}
