package com.bugtracker.bug_tracker.repository;

import com.bugtracker.bug_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
