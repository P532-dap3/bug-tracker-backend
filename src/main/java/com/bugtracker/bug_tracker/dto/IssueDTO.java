package com.bugtracker.bug_tracker.dto;

import com.bugtracker.bug_tracker.model.IssuePriority;
import com.bugtracker.bug_tracker.model.IssueStatus;

import java.time.LocalDateTime;
import java.util.List;

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

    public Long parentIssueId;
    public String parentIssueTitle;

    public List<IssueDTO> subIssues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUsername() {
        return assignedUsername;
    }

    public void setAssignedUsername(String assignedUsername) {
        this.assignedUsername = assignedUsername;
    }

    public Long getParentIssueId() {
        return parentIssueId;
    }

    public void setParentIssueId(Long parentIssueId) {
        this.parentIssueId = parentIssueId;
    }

    public String getParentIssueTitle() {
        return parentIssueTitle;
    }

    public void setParentIssueTitle(String parentIssueTitle) {
        this.parentIssueTitle = parentIssueTitle;
    }

    public List<IssueDTO> getSubIssues() {
        return subIssues;
    }

    public void setSubIssues(List<IssueDTO> subIssues) {
        this.subIssues = subIssues;
    }
}
