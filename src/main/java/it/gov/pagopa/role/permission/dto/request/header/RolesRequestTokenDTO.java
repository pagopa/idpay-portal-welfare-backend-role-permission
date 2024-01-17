package it.gov.pagopa.role.permission.dto.request.header;

import lombok.Data;

@Data
public class RolesRequestTokenDTO {

    private String role;
    private String partyRole;

}
