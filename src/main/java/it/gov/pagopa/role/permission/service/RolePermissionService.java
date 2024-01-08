package it.gov.pagopa.role.permission.service;

import it.gov.pagopa.role.permission.dto.UserPermissionDTO;
import org.springframework.stereotype.Service;

@Service
public interface RolePermissionService {

    UserPermissionDTO getUserPermission(String role);

}
