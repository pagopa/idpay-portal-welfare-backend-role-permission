package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ConsentDTO {
    @JsonProperty("consentCommittedDate")
    private LocalDate consentCommittedDate;

    @JsonProperty("label")
    private String label;

    @JsonProperty("sha")
    private String sha;

    @JsonProperty("link")
    private String link;
}
