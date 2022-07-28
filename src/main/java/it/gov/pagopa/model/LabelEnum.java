package it.gov.pagopa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import it.gov.pagopa.dto.ConsentItemDTO;


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
