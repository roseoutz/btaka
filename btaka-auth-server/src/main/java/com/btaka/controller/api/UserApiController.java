package com.btaka.controller.api;

import com.btaka.common.dto.SearchParam;
import com.btaka.data.user.dto.UserDTO;
import com.btaka.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/by/id/{userId}")
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

    @PostMapping("/flux/list")
    public Flux<ResponseEntity> getUsersFlux(@RequestBody SearchParam searchParam) {
        return userService.searchUser(searchParam)
                .flatMap(list -> Flux.just(ResponseEntity.ok(list)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PostMapping("/add")
    public Mono<ResponseEntity> addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PostMapping("/update")
    public Mono<ResponseEntity> updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @GetMapping("/delete/{oid}")
    public Mono<ResponseEntity> deleteUser(@PathVariable("oid") String oid) {
        return userService.deleteUser(oid)
                .flatMap(res -> Mono.just(ResponseEntity.ok(res)))
                .cast(ResponseEntity.class)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex)));
    }
}
