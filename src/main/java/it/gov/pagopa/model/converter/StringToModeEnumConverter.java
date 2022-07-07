package it.gov.pagopa.model.converter;

import it.gov.pagopa.model.Permission;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToModeEnumConverter implements Converter<String, Permission.Mode> {

    @Override
    public Permission.Mode convert(String source) {
        return Permission.Mode.valueOf(source);
    }
}