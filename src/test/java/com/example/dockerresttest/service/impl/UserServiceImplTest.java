package com.example.dockerresttest.service.impl;

import com.example.dockerresttest.model.User;
import com.example.dockerresttest.repository.UserRepository;
import com.example.dockerresttest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private AutoCloseable autoCloseable;
    private User testUser;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository);
        testUser = new User(9, "User 9", "Address 9");
        userService.addUser(testUser);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getUserService() {
        mock(User.class);
        mock(UserRepository.class);

        when(userRepository.findById(9)).thenReturn(Optional.of(testUser));
        assertThat(userService.getUserService(9)).isEqualTo(testUser);
    }

    @Test
    void getAllUsers() {
        mock(User.class);
        mock(UserRepository.class);
        User testUser2 = new User(99, "User 99", "Address 99");
        List<User> userList = Arrays.asList(testUser, testUser2);

        when(userRepository.findAll()).thenReturn(userList);
        assertThat(userService.getAllUsers()).isEqualTo(userList);

    }

    @Test
    void addUser() {
        mock(User.class);
        mock(UserRepository.class);

        when(userRepository.save(testUser)).thenReturn(testUser);
        assertThat(userService.addUser(testUser)).isEqualTo("User created successfully");
    }

    @Test
    void updateUser() {
        mock(User.class);
        mock(UserRepository.class);
        User updated = new User(9, "Updated Name", "Updated Address");

        when(userRepository.save(updated)).thenReturn(updated);
        assertThat(userService.updateUser(updated)).isEqualTo(updated);

    }

    @Test
    void deleteUser() {
        mock(User.class);
        mock(UserRepository.class, Mockito.CALLS_REAL_METHODS);
        String response = "User ID: " + testUser.getUserId() + " doesn't exist";

        doAnswer(Answers.CALLS_REAL_METHODS).when(userRepository).deleteById(any());
        assertThat(userService.deleteUser(9)).isEqualTo(response);
    }
}