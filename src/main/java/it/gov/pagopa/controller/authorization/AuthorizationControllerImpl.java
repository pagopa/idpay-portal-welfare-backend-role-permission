package it.gov.pagopa.controller.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.dto.UserPermissionDTO;
import it.gov.pagopa.dto.request.header.RolesRequestTokenDTO;
import it.gov.pagopa.dto.request.header.TokenPayloadDTO;
import it.gov.pagopa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${openapi.idPayWelfarePortalUserPermissions.base-path:/idpay/welfare/authorization}")
//@RequestMapping("/idpay/welfare/authorization")
public class AuthorizationControllerImpl implements AuthorizationController {

    @Autowired
    RoleService roleService;

    @Override
    public ResponseEntity<UserPermissionDTO> getUserPermissions(HttpServletRequest request) throws JsonProcessingException {

        TokenPayloadDTO payloadFromHeaderToken = getPayloadFromHeaderToken(request);
        List<RolesRequestTokenDTO> roles = payloadFromHeaderToken.getOrganizationRequestTokenDTO().getRoles();
        Optional<RolesRequestTokenDTO> firstRole = roles.stream().findFirst();
        if(firstRole.isPresent()){
            roleService.getUserPermission(firstRole.get().getRole());
        }
        return new ResponseEntity<UserPermissionDTO>(HttpStatus.OK);
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
