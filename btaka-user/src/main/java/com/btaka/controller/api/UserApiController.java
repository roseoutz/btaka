package com.btaka.controller.api;

import com.btaka.common.dto.SearchParam;
import com.btaka.data.user.dto.UserInfoDTO;
import com.btaka.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/id/{userId}")
    public Mono<ResponseEntity> getUserByUserId(@PathVariable("userId") String userId) {
        return userService.getUserByUserId(userId)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @GetMapping("/get/{oid}")
    public Mono<ResponseEntity> getUserById(@PathVariable("oid") String oid) {
        return userService.getUser(oid)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PostMapping("/list")
    public Mono<ResponseEntity> getUsers(@RequestBody SearchParam searchParam) {
        return userService.searchUser(searchParam)
                .collectList()
                .flatMap(list -> Mono.just(ResponseEntity.ok(list)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PostMapping("/user")
    public Mono<ResponseEntity> addUser(@RequestBody UserInfoDTO userInfoDTO) {
        return userService.addUser(userInfoDTO)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PutMapping("/user")
    public Mono<ResponseEntity> updateUser(@RequestBody UserInfoDTO userInfoDTO) {
        return userService.updateUser(userInfoDTO)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @DeleteMapping("/user/{oid}")
    public Mono<ResponseEntity> deleteUser(@PathVariable("oid") String oid) {
        return userService.deleteUser(oid)
                .flatMap(res -> Mono.just(ResponseEntity.ok(res)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex)));
    }
}
