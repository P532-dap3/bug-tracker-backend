package com.bugtracker.bug_tracker.service;

import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.*;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Issue> getAllActiveIssues() {
        return issueRepository.findByDeletedFalse();
    }

    public List<Issue> getIssuesByStatus(IssueStatus status) {
        return issueRepository.findByStatusAndDeletedFalse(status);
    }

    public Issue createIssueFromDTO(IssueDTO dto) {
        Project project = projectRepository.findById(dto.projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = null;
        if (dto.assignedUserId != null) {
            user = userRepository.findById(dto.assignedUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Issue issue = DTOMapper.toIssueEntity(dto, project, user);
        return issueRepository.save(issue);
    }

    public List<Issue> getIssuesByProject(Long projectId) {
        return issueRepository.findByProjectIdAndDeletedFalse(projectId);
    }

    public List<Issue> getIssuesByProjectAndStatus(Long projectId, IssueStatus status) {
        return issueRepository.findByProjectIdAndStatusAndDeletedFalse(projectId, status);
    }

    public List<Issue> getIssuesByUser(Long userId) {
        return issueRepository.findByAssignedUserIdAndDeletedFalse(userId);
    }

    public List<Issue> getIssuesByPriority(IssuePriority priority) {
        return issueRepository.findByPriorityAndDeletedFalse(priority);
    }

    public List<Issue> getIssuesByUserAndPriority(Long userId, IssuePriority priority) {
        return issueRepository.findByAssignedUserIdAndPriorityAndDeletedFalse(userId, priority);
    }

    public Issue assignUser(Long issueId, Long userId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        issue.setAssignedUser(user);
        return issueRepository.save(issue);
    }

    public Issue changeStatus(Long issueId, IssueStatus status) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setStatus(status);
        return issueRepository.save(issue);
    }

    public void softDelete(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setDeleted(true);
        issueRepository.save(issue);
    }
}
