package it.gov.pagopa.service;

import it.gov.pagopa.dto.PermissionDTO;
import it.gov.pagopa.dto.UserPermissionDTO;
import it.gov.pagopa.model.Role;
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
        Optional<Role> roleOptional = roleRepository.findByType(roleType);
        if(roleOptional.isPresent()){
            Role role = roleOptional.get();
            UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
            userPermissionDTO.setRole(role.getType());
            List<PermissionDTO> permissionDTOList = new ArrayList<>();
            BeanUtils.copyProperties(role.getPermissionList(), permissionDTOList, "description");
            userPermissionDTO.setPermissions(permissionDTOList);
            return userPermissionDTO;
        }
        return null;
    }
}
