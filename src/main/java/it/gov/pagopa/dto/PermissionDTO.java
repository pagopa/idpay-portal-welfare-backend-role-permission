package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * PermissionDTO
 */
@JsonPropertyOrder({
  PermissionDTO.JSON_PROPERTY_ID,
  PermissionDTO.JSON_PROPERTY_NAME,
  PermissionDTO.JSON_PROPERTY_MODE
})
@Data
public class PermissionDTO {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_MODE = "mode";
  private Mode mode;

  public enum Mode{
    R("READ"),
    W("WRITE");

    private String mode;

    Mode(String mode) {
      this.mode = mode;
    }
  }
}

