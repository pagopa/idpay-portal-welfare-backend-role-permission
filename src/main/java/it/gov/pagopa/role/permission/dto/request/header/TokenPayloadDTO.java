package it.gov.pagopa.role.permission.dto.request.header;

import lombok.Data;

@Data
public class TokenPayloadDTO {

    private String aud;
    private OrganizationRequestTokenDTO organizationRequestTokenDTO;
    private String iss;
    private String name;
    private Integer exp;
    private Integer iat;
    private String family_name;
    private String email;

}

