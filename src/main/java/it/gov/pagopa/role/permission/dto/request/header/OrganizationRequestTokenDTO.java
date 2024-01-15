package it.gov.pagopa.role.permission.dto.request.header;

import lombok.Data;

import java.util.List;

@Data
public class OrganizationRequestTokenDTO {

    private List<RolesRequestTokenDTO> roles;
    private String id;
    private String fiscal_code;

}
