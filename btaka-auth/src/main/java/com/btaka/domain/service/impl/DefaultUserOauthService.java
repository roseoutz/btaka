package com.btaka.domain.service.impl;

import com.btaka.domain.entity.UserOauthEntity;
import com.btaka.domain.repo.UserOauthRepository;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.dto.UserOauthDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;

@RequiredArgsConstructor
@Service("defaultUserOauthService")
public class DefaultUserOauthService implements UserOauthService {

    private final UserOauthRepository userOauthRepository;

    private final ModelMapper modelMapper;

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
                .switchIfEmpty(Mono.error(AccountNotFoundException::new));
    }

    @Override
    public Mono<UserOauthDTO> save(UserOauthDTO userOauthDTO) {
        return Mono.just(userOauthDTO)
                .map(oauthDTO -> modelMapper.map(oauthDTO, UserOauthEntity.class))
                .flatMap(userOauthEntity -> userOauthRepository.save(userOauthEntity))
                .map(userOauthEntity -> modelMapper.map(userOauthEntity, UserOauthDTO.class));
    }


}
