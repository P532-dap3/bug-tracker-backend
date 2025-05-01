package com.bugtracker.bug_tracker.dto;

import com.bugtracker.bug_tracker.model.IssuePriority;
import com.bugtracker.bug_tracker.model.IssueStatus;

import java.time.LocalDateTime;

public class IssueDTO {
    public Long id;
    public String title;
    public String description;
    public IssueStatus status;
    public IssuePriority priority;
    public boolean deleted;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public Long projectId;
    public String projectName;

    public Long assignedUserId;
    public String assignedUsername;
}

