package com.bugtracker.bug_tracker.service.impl;

import com.bugtracker.bug_tracker.exception.ResourceNotFoundException;
import com.bugtracker.bug_tracker.model.User;
import com.bugtracker.bug_tracker.repository.UserRepository;
import com.bugtracker.bug_tracker.service.BaseService;
import com.bugtracker.bug_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void validate(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
    }

    @Override
    protected User doSave(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        return save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}