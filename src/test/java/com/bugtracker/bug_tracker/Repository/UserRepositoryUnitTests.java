package com.bugtracker.bug_tracker.Repository;

import com.bugtracker.bug_tracker.model.User;
import com.bugtracker.bug_tracker.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryUnitTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Find user by username - success")
    void testFindByUsername_Success() {
        // Arrange
        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setRole("admin");

        userRepository.save(user);

        // Act
        Optional<User> result = userRepository.findByUsername("johndoe");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("john@example.com");
        assertThat(result.get().getRole()).isEqualTo("admin");
    }

    @Test
    @DisplayName("Find user by username - not found")
    void testFindByUsername_NotFound() {
        Optional<User> result = userRepository.findByUsername("nonexistent");

        assertThat(result).isNotPresent();
    }
}

