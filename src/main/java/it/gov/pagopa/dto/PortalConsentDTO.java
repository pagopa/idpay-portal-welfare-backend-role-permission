package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortalConsentDTO {

    @JsonProperty(value = "versionId")
    private String versionId;

    /*
    TODO keep it?
    @JsonProperty("history")
    private List<AcceptancesHistoryDTO> history;
     */
}
