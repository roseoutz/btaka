package com.btaka.cache.redis.service;

import com.btaka.cache.redis.repository.RedisCacheRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

public abstract class AbstractRedisCacheService<ID, T, D> {

    @Autowired
    private ModelMapper modelMapper;

    private final Class<D> dtoClass;

    private final Class<T> entityClass;

    public AbstractRedisCacheService(Class<D> dtoClass, Class<T> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    protected abstract CrudRepository<T, ID> getRepository();

    public Mono<D> save(D dto) {
        return Mono.just(dto)
                .publishOn(Schedulers.boundedElastic())
                .map(this::toEntity)
                .map(entity -> getRepository().save(entity))
                .flatMap(entity -> Mono.just(entity)
                        .map(this::toDto)
                );
    }

    public Mono<D> get(ID id) {
        return Mono.just(id)
                .publishOn(Schedulers.boundedElastic())
                .map(oid -> getRepository().findById(oid))
                .filter(Optional::isPresent)
                .map(entity -> toDto(entity.get()));
    }

    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    protected T toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    protected D toDto(T entity) {
        D dto = modelMapper.map(entity, dtoClass);
        return dto;
    }
}
