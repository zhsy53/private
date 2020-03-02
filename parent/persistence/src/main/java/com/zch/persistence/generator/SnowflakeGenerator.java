package com.zch.persistence.generator;

import com.zch.commons.util.IdGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeGenerator implements IdentifierGenerator {
    public static final String NAME = "snowflake";
    public static final String CLASS = "com.zch.persistence.generator.SnowflakeGenerator";
    private static final IdGenerator GENERATOR = IdGenerator.of();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return GENERATOR.generate();
    }
}
