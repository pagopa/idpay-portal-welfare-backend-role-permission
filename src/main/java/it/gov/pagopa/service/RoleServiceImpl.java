package it.gov.pagopa.service;

import it.gov.pagopa.dto.PermissionDTO;
import it.gov.pagopa.dto.UserPermissionDTO;
import it.gov.pagopa.model.Permission;
import it.gov.pagopa.model.RolePermission;
import it.gov.pagopa.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserPermissionDTO getUserPermission(String roleType) {
        Optional<RolePermission> roleOptional = roleRepository.findByRole(roleType);
        if(roleOptional.isPresent()){
            return rolePermissionToDTO(roleOptional.get());
        }
        return null;
    }

    private UserPermissionDTO rolePermissionToDTO(RolePermission rolePermission) {
        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        userPermissionDTO.setRole(rolePermission.getRole());
        List<PermissionDTO> permissionDTOList = new ArrayList<>();
//            BeanUtils.copyProperties(role.getPermissions(), permissionDTOList, "description");
        for (Permission source: rolePermission.getPermissions() ) {
            PermissionDTO permissionDTO= new PermissionDTO();
            BeanUtils.copyProperties(source , permissionDTO);
            permissionDTOList.add(permissionDTO);
        }
        userPermissionDTO.setPermissions(permissionDTOList);
        return userPermissionDTO;
    }

    public void saveRole() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole("test");
        rolePermission.setDescription("testDesc");
        roleRepository.save(rolePermission);
    }

}
