package com.btaka.domain.service.impl;

import com.btaka.board.common.constants.Roles;
import com.btaka.board.common.dto.User;
import com.btaka.domain.entity.UserEntity;
import com.btaka.domain.repo.UserRepository;
import com.btaka.domain.service.UserService;
import com.btaka.domain.web.dto.SignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public Mono<User> findByOid(String oid) {
        return userRepository.findById(oid)
                .map(userEntity -> {
                    User user = modelMapper.map(userEntity, User.class);
                    return user;
                })
                .switchIfEmpty(Mono.error(new Exception("user_not_found")));
    }

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
                .flatMap(userEntity -> Mono.just(modelMapper.map(userEntity, User.class)))
                .doOnError(throwable -> log.error("[BTAKA] SignUp Error", throwable));
    }


    @Override
    public Mono<Boolean> checkUserEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(userEntity != null))
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(modelMapper.map(userEntity, User.class)));
    }

    @Override
    public Mono<User> updateUser(String oid, User user) {
        return userRepository.findById(oid)
                .switchIfEmpty(Mono.error(new Exception("user_not_found")))
                .flatMap(userEntity -> {
                    if (!user.getAddress().equals(userEntity.getAddress())) {
                        userEntity.setAddress(user.getAddress());
                    }

                    if (!user.getAddressDetail().equals(userEntity.getAddressDetail())) {
                        userEntity.setAddressDetail(user.getAddressDetail());
                    }

                    if (!user.getMobile().equals(userEntity.getMobile())) {
                        userEntity.setMobile(user.getMobile());
                    }

                    if (!user.getPostNum().equals(userEntity.getPostNum())) {
                        userEntity.setPostNum(user.getPostNum());
                    }

                    return userRepository.save(userEntity);
                })
                .flatMap(userEntity -> Mono.just(modelMapper.map(userEntity, User.class)));
    }
}
