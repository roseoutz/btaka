package com.btaka.service.user.impl;

import com.btaka.common.dto.SearchParam;
import com.btaka.data.user.dto.UserInfoDTO;
import com.btaka.data.user.entity.UserInfoEntity;
import com.btaka.domain.user.UserMongoRepository;
import com.btaka.dto.ResponseDTO;
import com.btaka.service.user.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.UUID;

@Service
public class DefaultReactiveUserService implements UserService {

    private final UserMongoRepository userMongoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public DefaultReactiveUserService(UserMongoRepository userMongoRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.userMongoRepository = userMongoRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }



    @Override
    public Mono<ResponseDTO> getUser(String oid) {
        return userMongoRepository.findById(oid)
                .flatMap(data -> Mono
                        .just(new ResponseDTO(data))
                );
    }

    @Override
    public Mono<ResponseDTO> getUserByUserId(String userId) {
        return userMongoRepository.findByUserId(userId)
                .flatMap(data -> Mono
                        .just(new ResponseDTO(data))
                );
    }

    @Override
    public Flux<ResponseDTO> searchUser(SearchParam searchParam) {
        return Flux.just(searchParam)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(param -> {
                            Query searchQuery = new Query();
                            param.getParamMap()
                                    .forEach((key, value) -> searchQuery.addCriteria(Criteria.where(key).is(value)));

                            return reactiveMongoTemplate.find(searchQuery, UserInfoEntity.class, "user")
                                    .flatMap(data -> Mono
                                            .just(new ResponseDTO(data))
                                    );
                        }
                );
    }

    @Override
    @Transactional
    public Mono<ResponseDTO> addUser(UserInfoDTO userInfoDto) {
        return Mono.just(userInfoDto)
                .publishOn(Schedulers.boundedElastic())
                .map(userInfoDTO -> {
                    UserInfoEntity convertedEntity = new UserInfoEntity(userInfoDTO);
                    convertedEntity.setOid(UUID.randomUUID().toString());
                    return convertedEntity;
                })
                .flatMap(data -> Mono
                        .just(new ResponseDTO(data))
                );
    }

    @Override
    @Transactional
    public Mono<ResponseDTO> updateUser(UserInfoDTO userInfoDTO) {
        return userMongoRepository.findById(userInfoDTO.getOid())
                .publishOn(Schedulers.boundedElastic())
                .filter(Objects::nonNull)
                .map(entity -> {
                    if (!StringUtil.isNullOrEmpty(userInfoDTO.getUsername())) {
                        entity.setUsername(userInfoDTO.getUsername());
                    }

                    return entity;
                })
                .flatMap(userMongoRepository::save)
                .flatMap(data -> Mono
                        .just(new ResponseDTO(data))
                );
    }

    @Override
    public Mono<Void> deleteUser(String oid) {
        return userMongoRepository.deleteById(oid);
    }
}
