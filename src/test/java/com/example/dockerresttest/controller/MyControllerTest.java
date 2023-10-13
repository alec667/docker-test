package com.example.dockerresttest.controller;

import com.example.dockerresttest.model.User;
import com.example.dockerresttest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    void postUser() throws Exception {
        User nUser = new User(11, "User 11", "Address 11");
        String content = objectWriter.writeValueAsString(nUser);

        when(userService.addUser(nUser)).thenReturn("User created successfully");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(result ->
                        assertThat(result.getResponse().getContentAsString())
                                .isEqualTo("User created successfully"));
    }

    @Test
    void updateUser() throws Exception {
        User updatedUser = new User(9, "User 999", "Address 999");
        String content = objectWriter.writeValueAsString(updatedUser);

        when(userService.updateUser(updatedUser)).thenReturn(updatedUser);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.userName", Matchers.is(updatedUser.getUserName())));
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.deleteUser(9)).thenReturn("User ID: 9 DELETED");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/user/9");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertThat(result.getResponse().getContentAsString())
                                .isEqualTo("User ID: 9 DELETED"));
    }
}