package it.gov.pagopa.model;

import lombok.Data;

@Data
public class Permission {

    private String name;

    private String description;

//    @Convert(converter = ModeEnumToStringConverter.class)
    private String mode;
}
