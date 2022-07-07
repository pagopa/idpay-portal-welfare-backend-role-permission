package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * ErrorDTO
 */
@JsonPropertyOrder({
  ErrorDTO.JSON_PROPERTY_CODE,
  ErrorDTO.JSON_PROPERTY_MESSAGE
})
@Data
public class ErrorDTO {
  public static final String JSON_PROPERTY_CODE = "code";
  private Integer code;

  public static final String JSON_PROPERTY_MESSAGE = "message";
  private String message;

}

