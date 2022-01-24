package com.btaka.service.user.impl;

import com.btaka.common.dto.SearchParam;
import com.btaka.data.user.dto.UserDTO;
import com.btaka.data.user.entity.UserEntity;
import com.btaka.domain.user.UserMongoRepository;
import com.btaka.service.user.UserService;
import io.netty.util.internal.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

@Service
public class DefaultReactiveUserService implements UserService {

    @Resource(name = "${bean.crypto.password.encoder:bcPasswordEncoder}")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Mono<UserDTO> getUser(String oid) {
        return userMongoRepository.findById(oid)
                .flatMap(data -> Mono
                        .just(convertUserInfo(data))
                        .publishOn(Schedulers.boundedElastic())
                );
    }

    @Override
    public Mono<UserDTO> getUserByUserId(String userId) {
        return userMongoRepository.findByUserId(userId)
                .flatMap(data -> Mono
                        .just(convertUserInfo(data))
                        .publishOn(Schedulers.boundedElastic())
                );
    }

    @Override
    public Flux<UserDTO> searchUser(SearchParam searchParam) {
        return Flux.just(searchParam)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(param -> {
                            Query searchQuery = new Query();
                            param.getParamMap()
                                    .entrySet()
                                    .stream()
                                    .forEach(entry -> searchQuery.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue())));

                            return reactiveMongoTemplate.find(searchQuery, UserEntity.class, "user")
                                    .map(this::convertUserInfo);
                        }
                );
    }

    @Override
    @Transactional
    public Mono<UserDTO> addUser(UserDTO userDto) {
        return Mono.just(userDto)
                .publishOn(Schedulers.boundedElastic())
                .map(userDTO -> {
                    UserEntity convertedEntity = modelMapper.map(userDto, UserEntity.class);
                    convertedEntity.setOid(UUID.randomUUID().toString());
                    convertedEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

                    return convertedEntity;
                })
                .flatMap(data -> Mono
                        .just(convertUserInfo(data))
                        .publishOn(Schedulers.boundedElastic())
                );
    }

    @Override
    @Transactional
    public Mono<UserDTO> updateUser(UserDTO userDTO) {
        return userMongoRepository.findById(userDTO.getOid())
                .publishOn(Schedulers.boundedElastic())
                .filter(Objects::nonNull)
                .map(entity -> {
                    if (!StringUtil.isNullOrEmpty(userDTO.getUsername())) {
                        entity.setUsername(userDTO.getUsername());
                    }

                    if (!StringUtil.isNullOrEmpty(userDTO.getPassword())) {
                        entity.setPassword(userDTO.getPassword());
                    }

                    return entity;
                })
                .flatMap(entity -> userMongoRepository.save(entity))
                .flatMap(data -> Mono
                        .just(convertUserInfo(data))
                        .publishOn(Schedulers.boundedElastic())
                );
    }

    @Override
    public Mono<Void> deleteUser(String oid) {
        return userMongoRepository.deleteById(oid);
    }

    private UserDTO convertUserInfo(UserEntity userEntity) {
        userEntity.setPassword(null);
        userEntity.setOid(null);
        return modelMapper.map(userEntity, UserDTO.class);
    }
}
