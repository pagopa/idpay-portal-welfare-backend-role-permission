package it.gov.pagopa.model.converter;

import it.gov.pagopa.model.Permission;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ModeEnumToStringConverter implements Converter<Permission.Mode, String> {

    @Override
    public String convert(Permission.Mode source) {
        return source.getMode();
    }
}
