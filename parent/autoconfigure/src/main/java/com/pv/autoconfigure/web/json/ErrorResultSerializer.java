package com.pv.autoconfigure.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pv.autoconfigure.web.exception.domain.ErrorResult;

import java.io.IOException;

class ErrorResultSerializer extends StdSerializer<ErrorResult> {
    ErrorResultSerializer() {
        super(ErrorResult.class);
    }

    @Override
    public void serialize(ErrorResult value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeObjectField("message", value.message());
        gen.writeObjectField("display", value.display());

        gen.writeEndObject();
    }
}
