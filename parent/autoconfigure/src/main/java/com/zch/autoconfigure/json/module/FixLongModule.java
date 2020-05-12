package com.zch.autoconfigure.json.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;

import java.io.IOException;

public class FixLongModule extends SimpleModule {
    public FixLongModule() {
        super(PackageVersion.VERSION);

        addSerializer(long.class, new LongJsonSerializer());
        addSerializer(Long.class, new LongJsonSerializer());
    }

    private static final class LongJsonSerializer extends JsonSerializer<Long> {
        @Override
        public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }
}
