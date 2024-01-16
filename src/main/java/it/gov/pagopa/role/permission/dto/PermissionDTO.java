package it.gov.pagopa.role.permission.dto;

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
  public static final String JSON_PROPERTY_ID = "name";
  private String name;

  public static final String JSON_PROPERTY_NAME = "description";
  private String description;

  public static final String JSON_PROPERTY_MODE = "mode";
  private String mode;
}

