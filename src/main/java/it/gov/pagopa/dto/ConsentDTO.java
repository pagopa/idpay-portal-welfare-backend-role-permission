package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * ConsentDTO
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDTO {

  @JsonProperty("accepted")
  private Boolean accepted;

  @JsonProperty("changed")
  private Boolean changed;

  @JsonProperty("consents")
  @Valid
  private ConsentItemDTO consents;

}
