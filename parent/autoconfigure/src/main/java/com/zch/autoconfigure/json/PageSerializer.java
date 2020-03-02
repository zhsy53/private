package com.zch.autoconfigure.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.Page;

import java.io.IOException;

@SuppressWarnings("rawtypes")
class PageSerializer extends StdSerializer<Page> {
    PageSerializer() {
        super(Page.class);
    }

    @Override
    public void serialize(Page value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("page", value.getNumber());
        gen.writeNumberField("size", value.getSize());

        gen.writeNumberField("count", value.getTotalElements());

        gen.writeObjectField("content", value.getContent());

        gen.writeEndObject();
    }
}
