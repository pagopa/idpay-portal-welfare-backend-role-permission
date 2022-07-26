package it.gov.pagopa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Objects;

/**
 * List of consent
 */
@Schema(description = "List of consent")
@Validated

@Data
public class ConsentItemArrayDTO extends ArrayList<ConsentItemDTO>  {

}
