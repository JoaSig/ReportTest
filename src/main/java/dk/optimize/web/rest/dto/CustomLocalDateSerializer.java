package dk.optimize.web.rest.dto;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Date: 20/02/16
 */
public class CustomLocalDateSerializer extends JsonSerializer<LocalDate> {
    private static DateTimeFormatter formatter = DateTimeFormat
        .forPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.print(value));
    }
}
