package it.gov.pagopa.role.permission.service;

import it.gov.pagopa.role.permission.constants.RolePermissionConstants;
import it.gov.pagopa.role.permission.dto.PermissionDTO;
import it.gov.pagopa.role.permission.dto.UserPermissionDTO;
import it.gov.pagopa.role.permission.exception.PermissionNotFoundException;
import it.gov.pagopa.role.permission.model.Permission;
import it.gov.pagopa.role.permission.model.RolePermission;
import it.gov.pagopa.role.permission.repository.RolePermissionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public UserPermissionDTO getUserPermission(String roleType) {
        RolePermission roleOptional = rolePermissionRepository.findByRole(roleType).orElseThrow(() ->
                new PermissionNotFoundException(String.format(RolePermissionConstants.PERMISSIONS_NOT_FOUND_MSG, roleType))
        );
        return rolePermissionToDTO(roleOptional);
    }

    private UserPermissionDTO rolePermissionToDTO(RolePermission rolePermission) {
        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        userPermissionDTO.setRole(rolePermission.getRole());
        List<PermissionDTO> permissionDTOList = new ArrayList<>();
        if(rolePermission.getPermissions() != null) {
            for (Permission source : rolePermission.getPermissions()) {
                PermissionDTO permissionDTO = new PermissionDTO();
                BeanUtils.copyProperties(source, permissionDTO);
                permissionDTOList.add(permissionDTO);
            }
        }
        userPermissionDTO.setPermissions(permissionDTOList);
        return userPermissionDTO;
    }

}
