package com.btaka.domain.service.impl;

import com.btaka.board.common.constants.Roles;
import com.btaka.board.common.dto.User;
import com.btaka.common.exception.BtakaException;
import com.btaka.common.service.AbstractDataService;
import com.btaka.constant.AuthErrorCode;
import com.btaka.domain.entity.UserEntity;
import com.btaka.domain.repo.UserRepository;
import com.btaka.domain.service.UserService;
import com.btaka.domain.web.dto.PasswordChangeRequestDTO;
import com.btaka.domain.web.dto.SignUpRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class DefaultUserService extends AbstractDataService<UserEntity, User> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DefaultUserService() {
        super(UserEntity.class, User.class);
    }

    @Override
    public Mono<User> findByOid(final String oid) {
        return userRepository.findById(oid)
                .flatMap(userEntity -> Mono.just(toDto(userEntity)))
                .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public Mono<User> singUp(final SignUpRequestDTO requestDTO) {
        return checkUserEmail(requestDTO.getEmail())
                .flatMap(isCheck -> {
                    if (isCheck) {
                        return Mono.error(new BtakaException(AuthErrorCode.ALREADY_REGISTER_USER));
                    } else {
                        return Mono.just(true);
                    }
                })
                .then(Mono.just(requestDTO))
                .flatMap(dto -> {
                    String encPassword = passwordEncoder.encode(requestDTO.getPassword());

                    UserEntity user = UserEntity.builder()
                            .email(requestDTO.getEmail())
                            .username(requestDTO.getUsername())
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
                .flatMap(userEntity -> Mono.just(toDto(userEntity)))
                .doOnError(throwable -> log.error("[BTAKA] SignUp Error", throwable));
    }


    @Override
    public Mono<Boolean> checkUserEmail(final String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(userEntity != null))
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<User> findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(toDto(userEntity)));
    }

    @Override
    public Mono<User> updateUser(final String oid, final User user) {
        return Mono.just(user)
                .flatMap(inputUser ->
                    userRepository.findById(oid)
                            .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.USER_NOT_FOUND)))
                            .flatMap(userEntity -> {
                                if (!inputUser.getAddress().equals(userEntity.getAddress())) {
                                    userEntity.setAddress(inputUser.getAddress());
                                }

                                if (!inputUser.getAddressDetail().equals(userEntity.getAddressDetail())) {
                                    userEntity.setAddressDetail(inputUser.getAddressDetail());
                                }

                                if (!inputUser.getMobile().equals(userEntity.getMobile())) {
                                    userEntity.setMobile(inputUser.getMobile());
                                }

                                if (!inputUser.getPostNum().equals(userEntity.getPostNum())) {
                                    userEntity.setPostNum(inputUser.getPostNum());
                                }

                                if (!inputUser.getGender().equals(userEntity.getGender())) {
                                    userEntity.setGender(inputUser.getGender());
                                }

                                if (!inputUser.getUsername().equals(userEntity.getUsername())) {
                                    userEntity.setUsername(inputUser.getUsername());
                                }

                                return userRepository.save(userEntity);
                            })
                            .flatMap(userEntity -> Mono.just(toDto(userEntity)))
                );
    }

    @Override
    public Mono<User> changePassword(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        return Mono.just(passwordChangeRequestDTO)
                .flatMap(dto ->
                    userRepository.findById(dto.getOid())
                            .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.USER_NOT_FOUND)))
                            .flatMap(entity -> {
                                if (Objects.isNull(dto.getPassword())) {
                                    return Mono.error(new BtakaException(AuthErrorCode.PASSWORD_IS_EMPTY));
                                }

                                if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
                                    return Mono.error(new BtakaException(AuthErrorCode.PASSWORD_ORIGIN_NOT_MATCH));
                                }

                                if (!dto.getPassword().equals(dto.getPasswordCheck())) {
                                    return Mono.error(new BtakaException(AuthErrorCode.PASSWORD_CHECK_NOT_MATCH));
                                }

                                String encPassword = passwordEncoder.encode(dto.getPassword());
                                entity.setPassword(encPassword);
                                return userRepository.save(entity)
                                        .map(this::toDto);
                            })
                );
    }

    @Override
    public Mono<Boolean> lockUser(User user) {
        UserEntity entity = toEntity(user);
        entity.setLockedTime(LocalDateTime.now());
        entity.setLocked(true);
        return userRepository.save(entity)
                .then(Mono.just(true));
    }

    @Override
    public Mono<Boolean> unlockUser(User user) {
        UserEntity entity = toEntity(user);
        entity.setLocked(false);
        entity.setFailCount(0);
        return userRepository.save(entity)
                .then(Mono.just(true));
    }

    @Override
    public Mono<Boolean> loginFail(User user) {
        UserEntity entity = toEntity(user);
        entity.setFailCount(entity.getFailCount() + 1);
        return userRepository.save(entity)
                .then(Mono.just(true));
    }

    @Override
    public Mono<Boolean> loginSuccess(User user) {
        UserEntity entity = toEntity(user);
        entity.setLocked(false);
        entity.setFailCount(0);
        return userRepository.save(entity)
                .then(Mono.just(true));
    }
}
