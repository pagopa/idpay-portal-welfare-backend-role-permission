package it.gov.pagopa.repository;

import it.gov.pagopa.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String> {
}
