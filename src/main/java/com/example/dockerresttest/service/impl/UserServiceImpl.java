package com.example.dockerresttest.service.impl;

import com.example.dockerresttest.model.User;
import com.example.dockerresttest.repository.UserRepository;
import com.example.dockerresttest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserService(Integer userId) {
        User user = new User();
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            user = optional.get();
            return user;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String addUser(User user) {
        userRepository.save(user);
        return "User created successfully";
    }

    @Override
    public User updateUser(User user) {
        Optional<User> optional = userRepository.findById(user.getUserId());
        if (optional.isPresent()) {
            User updatedUser = optional.get();

            updatedUser.setUserId(user.getUserId());
            updatedUser.setUserName(user.getUserName());
            updatedUser.setUserAddress(user.getUserAddress());

            userRepository.save(updatedUser);
            return updatedUser;
        } else {
            userRepository.save(user);
            return user;
        }
    }

    @Override
    public String deleteUser(Integer userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            userRepository.deleteById(userId);
            return "User ID: " + userId + " DELETED";
        } else {
            return "User ID: " + userId + " doesn't exist";
        }
    }

}
