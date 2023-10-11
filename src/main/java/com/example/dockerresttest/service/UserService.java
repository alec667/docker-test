package com.example.dockerresttest.service;


import com.example.dockerresttest.model.User;

import java.util.List;

public interface UserService {
    User getUserService(Integer userId);
    List<User> getAllUsers();

    User addUser(User user);
}
