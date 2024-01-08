package it.gov.pagopa.role.permission.dto.onetrust;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
