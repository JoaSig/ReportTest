package dk.optimize.web.rest.dto;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonParser;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonToken;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.DeserializationContext;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Date: 20/02/16
 */
public class ISO8601LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            return ISODateTimeFormat.dateTimeParser().parseDateTime(str).toLocalDate();
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new LocalDate(jp.getLongValue());
        }
        throw ctxt.mappingException(handledType());
    }
}
