package com.zch.autoconfigure.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zch.commons.domain.PageData;

import java.io.IOException;

@SuppressWarnings("rawtypes")
class PageDataSerializer extends StdSerializer<PageData> {
    protected PageDataSerializer() {
        super(PageData.class);
    }

    @Override
    public void serialize(PageData value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("page", value.getPage());
        gen.writeNumberField("size", value.getSize());

        gen.writeNumberField("count", value.getCount());

        gen.writeObjectField("content", value.getContent());

        gen.writeEndObject();
    }
}
