package com.bugtracker.bug_tracker.Service;

import com.bugtracker.bug_tracker.model.User;
import com.bugtracker.bug_tracker.repository.UserRepository;
import com.bugtracker.bug_tracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setRole("admin");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        verify(userRepository).findAll();
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.createUser(user);

        assertThat(created.getUsername()).isEqualTo("johndoe");
        verify(userRepository).save(user);
    }

    @Test
    void testGetUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUser(1L);

        assertThat(found.getEmail()).isEqualTo("john@example.com");
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUser_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUser(2L));
    }

    @Test
    void testUpdateUser() {
        User updated = new User();
        updated.setUsername("johnny");
        updated.setEmail("johnny@example.com");
        updated.setRole("user");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updated);

        User result = userService.updateUser(1L, updated);

        assertThat(result.getUsername()).isEqualTo("johnny");
        assertThat(result.getEmail()).isEqualTo("johnny@example.com");
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }
}

