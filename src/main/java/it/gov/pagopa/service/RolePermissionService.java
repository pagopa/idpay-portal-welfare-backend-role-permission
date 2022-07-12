package it.gov.pagopa.service;

import it.gov.pagopa.dto.UserPermissionDTO;
import org.springframework.stereotype.Service;

@Service
public interface RolePermissionService {

    static final String PERMISSIONS_NOT_FOUND = "Permissions not found for [%s] role.";

    UserPermissionDTO getUserPermission(String role);

}
