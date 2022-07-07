package it.gov.pagopa.service;

import it.gov.pagopa.dto.UserPermissionDTO;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    UserPermissionDTO getUserPermission(String role);

}
