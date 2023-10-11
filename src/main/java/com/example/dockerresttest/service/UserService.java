package com.example.dockerresttest.service;


import com.example.dockerresttest.model.User;

import java.util.List;

public interface UserService {
    User getUserService(Integer userId);
    List<User> getAllUsers();
    String addUser(User user);
    User updateUser(User user);
}
