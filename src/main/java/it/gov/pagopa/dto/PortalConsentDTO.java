package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class PortalConsentDTO {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("accepted")
    private boolean accepted;

    @JsonProperty("changed")
    private boolean changed;

    @JsonProperty("acceptDate")
    private LocalDate acceptDate;

    @JsonProperty("consents")
    private List<ConsentDTO> consents;

    @JsonProperty("history")
    private List<AcceptancesHistoryDTO> history;
}
