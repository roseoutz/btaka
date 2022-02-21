package com.btaka.domain.service.impl;

import com.btaka.board.common.constants.Roles;
import com.btaka.board.common.dto.User;
import com.btaka.domain.entity.UserEntity;
import com.btaka.domain.repo.UserRepository;
import com.btaka.domain.service.UserService;
import com.btaka.dto.SignUpRequestDTO;
import com.btaka.security.dto.UserInfo;
import com.mongodb.DuplicateKeyException;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> singUp(SignUpRequestDTO requestDTO) {
        return checkUserEmail(requestDTO.getEmail())
                .flatMap(isCheck -> {
                    if (isCheck) {
                        return Mono.error(new Exception(""));
                    } else {
                        return Mono.just(true);
                    }
                })
                .then(Mono.just(requestDTO))
                .flatMap(dto -> {
                    String encPassword = passwordEncoder.encode(requestDTO.getPassword());

                    UserEntity user = UserEntity.builder()
                            .email(requestDTO.getEmail())
                            .username(requestDTO.getUserName())
                            .password(encPassword)
                            .mobile(requestDTO.getMobile())
                            .birthdate(requestDTO.getBirthdate())
                            .gender(requestDTO.getGender())
                            .postNum(requestDTO.getPostNum())
                            .address(requestDTO.getAddress())
                            .addressDetail(requestDTO.getAddressDetail())
                            .roles( "user".equalsIgnoreCase(requestDTO.getRole()) ? Roles.ROLE_USER : Roles.ROLE_MENTOR)
                            .build();

                    return userRepository.save(user);
                })
                .flatMap(userEntity -> Mono.just(new User(userEntity.getOid(), userEntity.getEmail(), userEntity.getUsername(), userEntity.getMobile(), null, userEntity.getRoles())))
                .doOnError(throwable -> log.error("[BTAKA] SignUp Error", throwable));
    }


    @Override
    public Mono<Boolean> checkUserEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(userEntity != null))
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<UserInfo> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(entity -> Mono.just(UserInfo.toUserInfo(entity)));
    }
}
