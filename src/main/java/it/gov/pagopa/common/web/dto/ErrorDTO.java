package it.gov.pagopa.common.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        ErrorDTO.JSON_PROPERTY_CODE,
        ErrorDTO.JSON_PROPERTY_MESSAGE
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ErrorDTO {

  @NotBlank
  public static final String JSON_PROPERTY_CODE = "code";
  private Integer code;

  @NotBlank
  public static final String JSON_PROPERTY_MESSAGE = "message";
  private String message;

}
