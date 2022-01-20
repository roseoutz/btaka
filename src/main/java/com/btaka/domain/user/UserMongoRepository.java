package com.btaka.domain.user;

import com.btaka.data.user.dto.UserDTO;
import com.btaka.data.user.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<UserDTO> findByUserId(String userId);
}
