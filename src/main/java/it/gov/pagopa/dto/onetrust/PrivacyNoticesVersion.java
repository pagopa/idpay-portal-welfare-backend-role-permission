package it.gov.pagopa.dto.onetrust;

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
public class PrivacyNoticesVersion {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "publishedDate")
    private LocalDateTime publishedDate;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "version")
    private int version;

}
