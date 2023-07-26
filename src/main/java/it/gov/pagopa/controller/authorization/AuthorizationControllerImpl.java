package it.gov.pagopa.controller.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.dto.UserPermissionDTO;
import it.gov.pagopa.dto.request.header.TokenPayloadDTO;
import it.gov.pagopa.service.RolePermissionService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationControllerImpl implements AuthorizationController {

    private final RolePermissionService rolePermissionService;

    public AuthorizationControllerImpl(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

//    @Override
//    public ResponseEntity<UserPermissionDTO> getUserPermissions(HttpServletRequest request) throws JsonProcessingException {
//
//        TokenPayloadDTO payloadFromHeaderToken = getPayloadFromHeaderToken(request);
//        List<RolesRequestTokenDTO> roles = payloadFromHeaderToken.getOrganizationRequestTokenDTO().getRoles();
//        Optional<RolesRequestTokenDTO> firstRole = roles.stream().findFirst();
//        if(firstRole.isPresent()){
//            roleService.getUserPermission(firstRole.get().getRole());
//        }
//        return new ResponseEntity<UserPermissionDTO>(HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<UserPermissionDTO> getUserPermissions(String role) throws JsonProcessingException {
        UserPermissionDTO userPermissionDTO = rolePermissionService.getUserPermission(role);
        return new ResponseEntity<UserPermissionDTO>(userPermissionDTO, HttpStatus.OK);
    }

    private TokenPayloadDTO getPayloadFromHeaderToken(HttpServletRequest request) throws JsonProcessingException {
        String token = request.getHeader("Authorization");

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        TokenPayloadDTO readValue = objectMapper.readValue(payload, TokenPayloadDTO.class);
        return readValue;
    }

}
