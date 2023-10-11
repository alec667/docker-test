package com.example.dockerresttest.controller;

import com.example.dockerresttest.model.User;
import com.example.dockerresttest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }



}
