package com.example.practtestjava.controller;

import com.example.practtestjava.model.User;
import com.example.practtestjava.repository.UserRepository;
import com.example.practtestjava.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;

    private User testUser;
    private final String API = "/users";
    private final String API_ID = "/users/{id}";

    private User createTestUser(long id, String name, LocalDate birthdate) {
        User user = new User(id, name, birthdate);
        return userService.save(user);
    }

    private List<User> createListTestUsers() {
        User user1 = new User(1, "Jane", LocalDate.of(1982, 11, 10));
        User user2 = new User(2, "Mick", LocalDate.of(2020, 9, 3));
        return List.of(user1, user2);
    }

    @Test
    @WithMockUser
    public void findAllUsersShouldReturnAllUsers() throws Exception {
        when(this.userService.findAll()).thenReturn(createListTestUsers());

        MvcResult result = mvc.perform(get(API))
                .andExpect(status().isOk())
                .andReturn();
        String expectedJSON = objectMapper.writeValueAsString(createListTestUsers());

        assertEquals(expectedJSON, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    void getUserById() throws Exception {
        User testUser = createTestUser(1, "Michail", LocalDate.of(1981, 11, 1));
        when(this.userService.findById(1)).thenReturn(createListTestUsers().get(0));

        MvcResult result = mvc.perform(get(API_ID, 1))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String expectedJSON = objectMapper.writeValueAsString(testUser);

        assertEquals(expectedJSON, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void shouldDeleteUserIfFound() throws Exception {
        doNothing().when(userService).deleteById(1);

        mvc.perform(delete(API_ID, 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateUser() throws Exception {
        User testUser = createTestUser(1, "Michail", LocalDate.of(1981, 11, 1));
        when(userService.save(testUser)).thenReturn(testUser);
        when(userService.findById(1)).thenReturn(testUser);

        mvc.perform(put(API_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void createNewUser() throws Exception {
        User testUser = createTestUser(1, "Michail", LocalDate.of(1981, 11, 1));

        MvcResult result = mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedJSON = objectMapper.writeValueAsString(testUser);

        assertEquals(expectedJSON, result.getResponse().getContentAsString());
    }

    @Test
    void searchUsersByBirthDateBetweenShouldReturnList() throws Exception {
        LocalDate birthDateFrom = LocalDate.of(1981, 01, 01);
        LocalDate birthDateTo = LocalDate.of(2023, 01, 01);
        when(this.userRepository.findAllByBirthDateBetween(birthDateFrom,
                birthDateTo)).thenReturn(createListTestUsers());
        mvc.perform(
                        get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}