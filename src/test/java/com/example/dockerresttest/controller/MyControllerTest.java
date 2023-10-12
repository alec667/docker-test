package com.example.dockerresttest.controller;

import com.example.dockerresttest.model.User;
import com.example.dockerresttest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyController.class)
class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private MyController myController;

    private AutoCloseable autoCloseable;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    User testUser1;
    User testUser2;
    List<User> userList;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(myController).build();
        testUser1 = new User(9, "User 9", "Address 9");
        testUser2 = new User(10, "User 10", "Address 10");
        userList = new ArrayList<>(Arrays.asList(testUser1, testUser2));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getUser() throws Exception {
        when(userService.getUserService(9)).thenReturn(testUser1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/user/9")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.userName", Matchers.is("User 9")))
                .andExpect(jsonPath("$.userAddress", Matchers.is("Address 9")));
    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userList);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/user/all")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].userName", Matchers.is("User 9")))
                .andExpect(jsonPath("$[1].userAddress", Matchers.is("Address 10")));
    }

    @Test
    void postUser() {
        
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}