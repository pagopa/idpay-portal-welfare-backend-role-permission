package it.gov.pagopa.role.permission.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class AcceptancesHistoryDTO {
    @JsonProperty("acceptDate")
    private LocalDate acceptDate;

    @JsonProperty("consents")
    private List<ConsentDTO> consents;
}
