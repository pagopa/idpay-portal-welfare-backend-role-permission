package it.gov.pagopa.repository;

import it.gov.pagopa.model.RolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RolePermissionRepository extends MongoRepository<RolePermission, String> {

    Optional<RolePermission> findByRole(String roleType);
}
