package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ConsentItemDTO
 */
@Validated

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
  @NotBlank
  private LabelEnum label;

  @JsonProperty("sha")
  @NotBlank
  private String sha;

  @JsonProperty("url")
  private String url;

}
