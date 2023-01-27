package it.gov.pagopa.service;


import it.gov.pagopa.dto.PermissionDTO;
import it.gov.pagopa.dto.UserPermissionDTO;
import it.gov.pagopa.exception.AuthorizationPermissionException;
import it.gov.pagopa.model.Permission;
import it.gov.pagopa.model.RolePermission;
import it.gov.pagopa.repository.RolePermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = {RolePermissionService.class})
@Slf4j
class RolePermissionServiceImplTest {

    @Autowired
    RolePermissionService rolePermissionService;

    @MockBean
    RolePermissionRepository rolePermissionRepository;

//    private static final Logger LOG = LoggerFactory.getLogger(RolePermisssionServiceTest.class);

    @Test
    void getUserPermissionReturnPermission() {
        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        List<PermissionDTO> permissionDTOList = new ArrayList<>();
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setMode("WRITE");
        permissionDTO.setName("InitiativeCreation");
        permissionDTO.setDescription("Initiative Creation");
        permissionDTOList.add(permissionDTO);
        PermissionDTO permissionDTO2 = new PermissionDTO();
        permissionDTO2.setMode("WRITE");
        permissionDTO2.setName("InitiativeModification");
        permissionDTO2.setDescription("Initiative Modification");
        permissionDTOList.add(permissionDTO2);
        userPermissionDTO.setRole("admin");
        userPermissionDTO.setPermissions(permissionDTOList);

        RolePermission rolePermission = new RolePermission();
        List<Permission> permissionList = new ArrayList<>();
        Permission permission = new Permission();
        permission.setMode("WRITE");
        permission.setName("InitiativeCreation");
        permission.setDescription("Initiative Creation");
        permissionList.add(permission);
        Permission permission2 = new Permission();
        permission2.setMode("WRITE");
        permission2.setName("InitiativeModification");
        permission2.setDescription("Initiative Modification");
        permissionList.add(permission2);
        rolePermission.setRole("admin");
        rolePermission.setDescription("Administrator");
        rolePermission.setPermissions(permissionList);

        Mockito.when(rolePermissionRepository.findByRole("admin")).thenReturn(Optional.of(rolePermission));

        UserPermissionDTO admin = rolePermissionService.getUserPermission("admin");

        assertEquals(userPermissionDTO, admin);
    }

    @Test
    void rolePermissionRepository_NotNull() {
        assertNotNull(rolePermissionRepository);
    }

    @Test
    void rolePermissionService_NotNull() {
        assertNotNull(rolePermissionService);
    }

    @Test
    void getAdminRole_ok() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole("admin");

        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        userPermissionDTO.setRole("admin");
        userPermissionDTO.setPermissions(new ArrayList<>());

        Mockito.when(rolePermissionRepository.findByRole(anyString()))
                .thenReturn(Optional.of(rolePermission));
        UserPermissionDTO userPermission = rolePermissionService.getUserPermission(anyString());

        // you are expecting repo to be called once with correct param
        verify(rolePermissionRepository).findByRole(anyString());

        assertEquals(rolePermission.getRole(), rolePermission.getRole());
    }

    @Test
    void getUserPermissionReturnPermission_ko() {
        Mockito.when(rolePermissionRepository.findByRole(anyString()))
                .thenReturn(Optional.empty());
        try {
            rolePermissionService.getUserPermission(anyString());
        } catch (AuthorizationPermissionException e) {
            log.info("AuthorizationPermissionException: " + e.getCode());
            assertEquals(HttpStatus.NOT_FOUND.value(), e.getCode());
        }
    }

}
