package com.bugtracker.bug_tracker.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    private String username;
    private String email;
    private String role;

    // Could add password fields if needed for auth

    @OneToMany(mappedBy = "assignedToUser")
    private List<Issue> assignedIssues;
}
