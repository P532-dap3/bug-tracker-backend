package com.bugtracker.bug_tracker.Controller;

import com.bugtracker.bug_tracker.controller.UserController;
import com.bugtracker.bug_tracker.dto.UserDTO;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.model.User;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import com.bugtracker.bug_tracker.service.ProjectService;
import com.bugtracker.bug_tracker.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        public UserRepository userRepository(){
            return Mockito.mock(UserRepository.class);
        }
    }

    private User sampleUser;

    private UserDTO sampleUserDTO = new UserDTO();

    @BeforeEach
    public void setup() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("john_doe");
        sampleUser.setEmail("john@example.com");
        sampleUser.setRole("Developer");
        sampleUserDTO.id = 1L;
        sampleUserDTO.username = "john_doe";
        sampleUserDTO.email = "john@example.com";
        sampleUserDTO.role = "Developer";
    }
    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        List<User> users = List.of(sampleUser);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(sampleUser.getId().intValue())))
                .andExpect(jsonPath("$[0].username", is("john_doe")))
                .andExpect(jsonPath("$[0].email", is("john@example.com")))
                .andExpect(jsonPath("$[0].role", is("Developer")));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        User inputUser = new User();
        inputUser.setId(null);
        inputUser.setUsername("john_doe");
        inputUser.setEmail("john@example.com");
        inputUser.setRole("Developer");
        Mockito.when(userService.createUser(any(User.class))).thenReturn(sampleUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("john_doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")))
                .andExpect(jsonPath("$.role", is("Developer")));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        Mockito.when(userService.getUser(1L)).thenReturn(sampleUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("john_doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")))
                .andExpect(jsonPath("$.role", is("Developer")));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("john_doe_updated");
        updatedUser.setEmail("john_updated@example.com");
        updatedUser.setRole("Developer");

        UserDTO updatedDTO = new UserDTO();
        updatedDTO.id = 1L;
        updatedDTO.username = "john_doe";
        updatedDTO.email = "john@example.com";
        updatedDTO.role = "Developer";

        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("john_doe_updated")))
                .andExpect(jsonPath("$.email", is("john_updated@example.com")))
                .andExpect(jsonPath("$.role", is("Developer")));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
}

