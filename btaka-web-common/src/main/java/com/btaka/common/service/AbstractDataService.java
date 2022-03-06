package com.btaka.common.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDataService<T, D> {

    private final Class<T> entityClass;
    private final Class<D> dtoClass;

    public AbstractDataService(Class<T> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Autowired
    protected ModelMapper modelMapper;

    protected T toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    protected D toDto(T entity) {
        return modelMapper.map(entity, dtoClass);
    }
}
