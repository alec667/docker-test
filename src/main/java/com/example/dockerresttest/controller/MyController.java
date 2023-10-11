package com.example.dockerresttest.controller;

import com.example.dockerresttest.model.User;
import com.example.dockerresttest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class MyController {

    @Autowired
    UserService userService;

    @GetMapping(path = "{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getUserService(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<String> postUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }


}
