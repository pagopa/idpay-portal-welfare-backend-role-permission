package it.gov.pagopa.repository;

import it.gov.pagopa.model.RolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolePermissionRepository extends MongoRepository<RolePermission, String> {

    Optional<RolePermission> findByRole(String roleType);

}
