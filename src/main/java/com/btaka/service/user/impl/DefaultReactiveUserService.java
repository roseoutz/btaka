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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
public class DefaultReactiveUserService implements UserService {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Mono<UserDTO> getUser(String oid) {
        return userMongoRepository.findById(oid)
                .map(this::convertUserInfo);
    }

    @Override
    public Mono<UserDTO> getUserByUserId(String userId) {
        return userMongoRepository.findByUserId(userId)
                .map(this::convertUserInfo);
    }

    @Override
    public Flux<UserDTO> searchUser(SearchParam searchParam) {
        Query searchQuery = new Query();

        if (!searchParam.getParamMap().isEmpty()) {
            searchParam.getParamMap()
                    .forEach((key, value) -> searchQuery.addCriteria(Criteria.where(key).is(value)));
        }

        return reactiveMongoTemplate.find(searchQuery, UserEntity.class, "user")
                .map(this::convertUserInfo);
    }

    @Override
    @Transactional
    public Mono<UserDTO> addUser(UserDTO userDto) {
        UserEntity convertedEntity = modelMapper.map(userDto, UserEntity.class);
        convertedEntity.setOid(UUID.randomUUID().toString());
        return userMongoRepository.save(convertedEntity)
                .map(this::convertUserInfo);
    }

    @Override
    @Transactional
    public Mono<UserDTO> updateUser(UserDTO userDTO) {
        return userMongoRepository.findById(userDTO.getOid())
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
                .map(this::convertUserInfo);
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
