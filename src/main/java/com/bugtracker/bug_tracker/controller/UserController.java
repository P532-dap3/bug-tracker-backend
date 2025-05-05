package com.bugtracker.bug_tracker.controller;

import com.bugtracker.bug_tracker.dto.UserDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.User;
import com.bugtracker.bug_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username);
        user.setEmail(userDTO.email);
        user.setRole(userDTO.role);

        User savedUser = userService.createUser(user);
        return DTOMapper.toUserDTO(savedUser);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return DTOMapper.toUserDTO(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = new User();
        updatedUser.setUsername(userDTO.username);
        updatedUser.setEmail(userDTO.email);
        updatedUser.setRole(userDTO.role);

        User savedUser = userService.updateUser(id, updatedUser);
        return DTOMapper.toUserDTO(savedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
