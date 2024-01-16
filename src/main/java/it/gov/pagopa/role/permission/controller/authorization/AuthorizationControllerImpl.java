package it.gov.pagopa.role.permission.controller.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.gov.pagopa.role.permission.dto.UserPermissionDTO;
import it.gov.pagopa.role.permission.service.RolePermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationControllerImpl implements AuthorizationController {

    private final RolePermissionService rolePermissionService;

    public AuthorizationControllerImpl(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public ResponseEntity<UserPermissionDTO> getUserPermissions(String role) throws JsonProcessingException {
        UserPermissionDTO userPermissionDTO = rolePermissionService.getUserPermission(role);
        return new ResponseEntity<>(userPermissionDTO, HttpStatus.OK);
    }

}
