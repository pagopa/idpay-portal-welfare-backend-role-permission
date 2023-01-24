package it.gov.pagopa.dto.onetrust;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PrivacyNoticesDTO {

    @JsonProperty(value = "createdDate")
    private LocalDateTime createdDate;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "lastPublishedDate")
    private LocalDateTime lastPublishedDate;

    @JsonProperty(value = "organizationId")
    private String organizationId;

    @JsonProperty(value = "responsibleUserId")
    private String responsibleUserId;

    @JsonProperty(value = "version")
    private PrivacyNoticesVersion version;

}
