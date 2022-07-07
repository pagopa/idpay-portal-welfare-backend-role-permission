package it.gov.pagopa.dto.request.header;

import it.gov.pagopa.dto.request.header.RolesRequestTokenDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationRequestTokenDTO {

    private List<RolesRequestTokenDTO> roles;
    private String id;
    private String fiscal_code;

}
