package com.zch.autoconfigure.dozer;

import org.dozer.DozerBeanMapper;

import java.util.List;

public abstract class DozerFactory {
    public static DozerBeanMapper MAPPER;

    static {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(List.of("dozerJdk8Converters.xml"));
        MAPPER = mapper;
    }
}
