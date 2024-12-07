package com.youtube.uploader.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.uploader.controllers.requests.CreateUserRequest;
import com.youtube.uploader.controllers.responses.GetUsersResponse;
import com.youtube.uploader.models.User;
import com.youtube.uploader.services.UserService;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/api/user")
@Async
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<?>> createUser(@RequestBody CreateUserRequest user) {
        userService.createUser(user.firstname, user.lastname, user.providerId, user.providerType);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.ACCEPTED));
    }

    @GetMapping
    public CompletableFuture<?> getUsers() {
        List<User> users = userService.getUsers();
        GetUsersResponse res = GetUsersResponse.builder()
                .users(users)
                .build();
        return CompletableFuture.completedFuture(res);
    }
}
