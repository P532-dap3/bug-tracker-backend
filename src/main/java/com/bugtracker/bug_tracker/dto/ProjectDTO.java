package com.bugtracker.bug_tracker.dto;

import java.time.LocalDateTime;

public class ProjectDTO {
    public Long id;
    public String name;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
