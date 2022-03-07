package com.btaka.common.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public abstract class AbstractDataService<T, D> {

    private final Class<T> entityClass;
    private final Class<D> dtoClass;

    public AbstractDataService(Class<T> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Autowired
    protected ModelMapper modelMapper;

    protected String getUUID() {
        return Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }

    protected T toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    protected D toDto(T entity) {
        return modelMapper.map(entity, dtoClass);
    }
}
