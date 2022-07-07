package it.gov.pagopa.model;

import it.gov.pagopa.model.converter.ModeEnumToStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("permission")
public class Permission {
//    INI_CREATE ("intiativeCreation"),
//    INI_EDIT ("intiativeModification"),
//    INI_DELETE ("intiativeDeletion");

    @Id
    //    private ObjectId id;
    private String id;

    private String name;

    private String description;

//    @Convert(converter = ModeEnumToStringConverter.class)
    private Mode mode;

    @Getter
    public enum Mode{
        R("READ"),
        W("WRITE");

        private String mode;

        Mode(String mode) {
            this.mode = mode;
        }
    }
}
