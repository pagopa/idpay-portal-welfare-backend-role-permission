package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PortalConsentDTO {
    @JsonProperty(value = "userId")
    private String userId;

    @JsonProperty(value = "acceptDate")
    private LocalDate acceptDate;

    @JsonProperty(value = "versionId")
    private String versionId;

    /*
    TODO keep it?
    @JsonProperty("history")
    private List<AcceptancesHistoryDTO> history;
     */
}
