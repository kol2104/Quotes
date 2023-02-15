package com.kameleoon.users.controller;

import com.kameleoon.users.model.User;
import com.kameleoon.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            return new ResponseEntity(userOptional, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<User> getUserByNameAndPassword(@RequestParam String name, @RequestParam String password) {
        Optional<User> userOptional = userService.getUserByNameAndPassword(name, password);
        if (userOptional.isPresent()) {
            return new ResponseEntity(userOptional, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
    }
}
