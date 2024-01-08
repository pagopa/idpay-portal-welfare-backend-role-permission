package it.gov.pagopa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.role.permission.config.ServiceExceptionConfig;
import it.gov.pagopa.role.permission.constants.RolePermissionConstants;
import it.gov.pagopa.role.permission.controller.authorization.AuthorizationController;
import it.gov.pagopa.role.permission.dto.PermissionDTO;
import it.gov.pagopa.role.permission.dto.UserPermissionDTO;
import it.gov.pagopa.role.permission.exception.PermissionNotFoundException;
import it.gov.pagopa.role.permission.model.Permission;
import it.gov.pagopa.role.permission.model.RolePermission;
import it.gov.pagopa.role.permission.repository.RolePermissionRepository;
import it.gov.pagopa.role.permission.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = {
        AuthorizationController.class, ServiceExceptionConfig.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Slf4j
class AuthorizationControllerTest {

    @Mock
    RolePermissionRepository rolePermissionRepository;

    @MockBean
    RolePermissionService rolePermissionServiceMock;

    @Autowired
    protected MockMvc mvc;

    private static final String BASE_URL = "http://localhost:8080/idpay/authorization";
    private static final String PERMISSIONS_URL = "/permissions/";
    private static final String ROLE = "TEST_ROLE";

    RolePermission createAdminRolePermission () {
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
        return rolePermission;
    }

    UserPermissionDTO createAdminPermissionDTO () {
        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        List<PermissionDTO> permissionDTOList = new ArrayList<>();
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setMode("WRITE");
        permissionDTO.setName("InitiativeCreation");
        permissionDTO.setDescription("Initiative Creation");
        PermissionDTO permissionDTO2 = new PermissionDTO();
        permissionDTO2.setMode("WRITE");
        permissionDTO2.setName("InitiativeCreation");
        permissionDTO2.setDescription("Initiative Creation");
        permissionDTOList.add(permissionDTO);
        permissionDTOList.add(permissionDTO2);
        userPermissionDTO.setRole("admin");
        userPermissionDTO.setPermissions(permissionDTOList);
        return userPermissionDTO;
    }

    @Test
    void shouldReturnPermission() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserPermissionDTO adminPermissionDTO = createAdminPermissionDTO();
        UserPermissionDTO dummyAdminPermissionDTO = new UserPermissionDTO();
        dummyAdminPermissionDTO.setRole("admin");
        dummyAdminPermissionDTO.setPermissions(Arrays.asList(new PermissionDTO()));

        // Returning something from Repo by using ServiceMock
        when(rolePermissionServiceMock.getUserPermission(anyString())).thenReturn(dummyAdminPermissionDTO);

        // When
        UserPermissionDTO adminPermissions = rolePermissionServiceMock.getUserPermission("admin");

        // Then
        // you are expecting service to return whatever returned by repo
        assertThat("result", adminPermissions, is(sameInstance(dummyAdminPermissionDTO)));

        mvc.perform(
            MockMvcRequestBuilders.get(BASE_URL + PERMISSIONS_URL + "admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.role").value(dummyAdminPermissionDTO.getRole()))
                .andExpect(jsonPath("$.permissions[0].name").value(dummyAdminPermissionDTO.getPermissions().get(0).getName()))
                .andExpect(jsonPath("$.permissions[0].description").value(dummyAdminPermissionDTO.getPermissions().get(0).getDescription()))
                .andExpect(jsonPath("$.permissions[0].mode").value(dummyAdminPermissionDTO.getPermissions().get(0).getMode()))
                .andDo(print())
                .andReturn();

    }

    @Test
    void shouldReturnNotFound() throws Exception {
        Mockito.doThrow(new PermissionNotFoundException(String.format(RolePermissionConstants.PERMISSIONS_NOT_FOUND_MSG, ROLE)))
                .when(rolePermissionServiceMock).getUserPermission(anyString());

        MvcResult mvcResult = mvc.perform(get(BASE_URL + PERMISSIONS_URL + "/{role}", ROLE))
                .andExpect(status().isNotFound())
                .andExpect(getResult -> Assertions.assertTrue(getResult.getResolvedException() instanceof PermissionNotFoundException))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();


        Optional<PermissionNotFoundException> authorizationPermissionException = Optional.ofNullable((PermissionNotFoundException) mvcResult.getResolvedException());

        authorizationPermissionException.ifPresent( (authExp) -> assertThat(authExp, is(notNullValue())));
        authorizationPermissionException.ifPresent( (authExp) -> assertThat(authExp, is(instanceOf(PermissionNotFoundException.class))));

    }

}