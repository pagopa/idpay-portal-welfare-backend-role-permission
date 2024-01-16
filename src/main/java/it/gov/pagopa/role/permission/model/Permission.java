package it.gov.pagopa.role.permission.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    private String name;

    private String description;

//    @Convert(converter = ModeEnumToStringConverter.class)
    private String mode;
}
