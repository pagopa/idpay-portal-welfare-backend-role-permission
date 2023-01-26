package it.gov.pagopa.service;

import it.gov.pagopa.dto.PermissionDTO;
import it.gov.pagopa.dto.UserPermissionDTO;
import it.gov.pagopa.exception.AuthorizationPermissionException;
import it.gov.pagopa.model.Permission;
import it.gov.pagopa.model.RolePermission;
import it.gov.pagopa.repository.RolePermissionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Override
    public UserPermissionDTO getUserPermission(String roleType) {
        RolePermission roleOptional = rolePermissionRepository.findByRole(roleType).orElseThrow(() ->
                new AuthorizationPermissionException(HttpStatus.NOT_FOUND.value(),
                        String.format(PERMISSIONS_NOT_FOUND, roleType))
        );
        return rolePermissionToDTO(roleOptional);
    }

    private UserPermissionDTO rolePermissionToDTO(RolePermission rolePermission) {
        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        userPermissionDTO.setRole(rolePermission.getRole());
        List<PermissionDTO> permissionDTOList = new ArrayList<>();
//            BeanUtils.copyProperties(role.getPermissions(), permissionDTOList, "description");
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

    public void saveRole() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole("test");
        rolePermission.setDescription("testDesc");
        rolePermissionRepository.save(rolePermission);
    }

}
