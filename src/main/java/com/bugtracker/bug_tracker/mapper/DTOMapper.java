package com.bugtracker.bug_tracker.mapper;

import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.dto.ProjectDTO;
import com.bugtracker.bug_tracker.dto.UserDTO;
import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.model.User;

public class DTOMapper {

    // --- Project ---
    public static ProjectDTO toProjectDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.id = project.getId();
        dto.name = project.getName();
        dto.description = project.getDescription();
        dto.createdAt = project.getCreatedAt();
        dto.updatedAt = project.getUpdatedAt();
        return dto;
    }

    public static Project toProjectEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setId(dto.id);
        project.setName(dto.name);
        project.setDescription(dto.description);
        return project;
    }

    // --- User ---
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        return dto;
    }

    public static IssueDTO toIssueDTO(Issue issue) {
        IssueDTO dto = new IssueDTO();
        dto.id = issue.getId();
        dto.title = issue.getTitle();
        dto.description = issue.getDescription();
        dto.status = issue.getStatus();
        dto.priority = issue.getPriority();
        dto.deleted = issue.isDeleted();
        dto.createdAt = issue.getCreatedAt();
        dto.updatedAt = issue.getUpdatedAt();

        if (issue.getProject() != null) {
            dto.projectId = issue.getProject().getId();
            dto.projectName = issue.getProject().getName();
        }

        if (issue.getAssignedUser() != null) {
            dto.assignedUserId = issue.getAssignedUser().getId();
            dto.assignedUsername = issue.getAssignedUser().getUsername();
        }

        if (issue.getParentIssue() != null) {
            dto.parentIssueId = issue.getParentIssue().getId();
            dto.parentIssueTitle = issue.getParentIssue().getTitle();
        } else {
            dto.parentIssueId = -1L;
            dto.parentIssueTitle = "";
        }

        if (issue.getSubIssues() != null) {
            dto.subIssues = issue.getSubIssues().stream()
                    .map(DTOMapper::toIssueDTO)
                    .toList();
        }

        return dto;
    }


    public static Issue toIssueEntity(IssueDTO dto, Project project, User assignedUser, Issue parentIssue) {
        Issue issue = new Issue();
        issue.setTitle(dto.title);
        issue.setDescription(dto.description);
        issue.setStatus(dto.status);
        issue.setPriority(dto.priority);
        issue.setDeleted(dto.deleted);
        issue.setProject(project);
        issue.setAssignedUser(assignedUser);
        issue.setParentIssue(parentIssue);
        return issue;
    }
}

