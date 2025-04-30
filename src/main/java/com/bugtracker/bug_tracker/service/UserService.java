package com.bugtracker.bug_tracker.service;

import com.bugtracker.bug_tracker.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
}
