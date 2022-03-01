package com.btaka.domain.service.impl;

import com.btaka.common.service.AbstractDataService;
import com.btaka.domain.entity.UserOauthEntity;
import com.btaka.domain.repo.UserOauthRepository;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.dto.UserOauthDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service("defaultUserOauthService")
public class DefaultUserOauthService extends AbstractDataService<UserOauthEntity, UserOauthDTO> implements UserOauthService {

    @Autowired
    private UserOauthRepository userOauthRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DefaultUserOauthService() {
        super(UserOauthEntity.class, UserOauthDTO.class);
    }

    @Override
    public Mono<UserOauthDTO> get(String oauthSite, String userOid) {
        return userOauthRepository.findByUserOidAndOauthSite(userOid, oauthSite)
                .map(userOauthEntity -> modelMapper.map(userOauthEntity, UserOauthDTO.class))
                .switchIfEmpty(Mono.just(new UserOauthDTO()));
    }

    @Override
    public Mono<UserOauthDTO> getByOauthId(String oauthId) {
        return userOauthRepository.findByOauthId(oauthId)
                .map(userOauthEntity -> modelMapper.map(userOauthEntity, UserOauthDTO.class))
                .switchIfEmpty(Mono.just(new UserOauthDTO()));
    }

    @Override
    public Mono<UserOauthDTO> save(UserOauthDTO userOauthDTO) {
        return Mono.just(userOauthDTO)
                .map(oauthDTO -> modelMapper.map(oauthDTO, UserOauthEntity.class))
                .flatMap(userOauthRepository::save)
                .map(userOauthEntity -> modelMapper.map(userOauthEntity, UserOauthDTO.class));
    }


}
