package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * ConsentItemDTO
 */
@Validated

@Data
public class ConsentItemDTO   {
  /**
   * Gets or Sets label
   */
  public enum LabelEnum {

    PRIVACY("privacy"),
    
    TC("tc");

    private String value;

    LabelEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LabelEnum fromValue(String text) {
      for (LabelEnum b : LabelEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("label")
  private LabelEnum label = null;

  @JsonProperty("sha")
  private String sha = null;

  @JsonProperty("url")
  private String url = null;


}
