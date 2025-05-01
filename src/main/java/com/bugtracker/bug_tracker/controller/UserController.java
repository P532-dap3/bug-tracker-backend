package com.bugtracker.bug_tracker.controller;

import com.bugtracker.bug_tracker.dto.UserDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.User;
import com.bugtracker.bug_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username);
        user.setEmail(userDTO.email);
        user.setRole(userDTO.role);

        User savedUser = userRepository.save(user);
        return DTOMapper.toUserDTO(savedUser);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return DTOMapper.toUserDTO(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}

