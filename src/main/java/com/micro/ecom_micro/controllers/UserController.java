package com.micro.ecom_micro.controllers;

import com.micro.ecom_micro.dto.UserRequest;
import com.micro.ecom_micro.dto.UserResponse;
import com.micro.ecom_micro.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest updatedUserRequest,
                                             @PathVariable Long id) {
        boolean updated = userService.updateUser(id, updatedUserRequest);
        if (updated)
            return ResponseEntity.ok("User Updated");
        return ResponseEntity.notFound().build();
    }
}
