package com.pv.persistence.generator;

import com.pv.commons.util.IdGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeGenerator implements IdentifierGenerator {
    public static final String NAME = "snowflake";
    public static final String CLASS = "com.pv.persistence.generator.SnowflakeGenerator";
    private static final IdGenerator GENERATOR = IdGenerator.of();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return GENERATOR.generate();
    }
}