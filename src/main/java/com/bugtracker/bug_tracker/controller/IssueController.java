package com.bugtracker.bug_tracker.controller;

import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.*;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<IssueDTO> getAllIssues() {
        return issueRepository.findByDeletedFalse().stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/status/{status}")
    public List<Issue> getByStatus(@PathVariable IssueStatus status) {
        return issueRepository.findByStatus(status);
    }

    @PostMapping
    public IssueDTO createIssue(@RequestBody IssueDTO dto) {
        Project project = projectRepository.findById(dto.projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = null;
        if (dto.assignedUserId != null) {
            user = userRepository.findById(dto.assignedUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Issue issue = DTOMapper.toIssueEntity(dto, project, user);
        Issue saved = issueRepository.save(issue);
        return DTOMapper.toIssueDTO(saved);
    }

    @GetMapping("/project/{projectId}")
    public List<IssueDTO> getIssuesByProject(@PathVariable Long projectId) {
        return issueRepository.findByProjectIdAndDeletedFalse(projectId).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/project/{projectId}/status/{status}")
    public List<IssueDTO> getIssuesByProjectAndStatus(@PathVariable Long projectId, @PathVariable IssueStatus status) {
        return issueRepository.findByProjectIdAndStatusAndDeletedFalse(projectId, status).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<IssueDTO> getIssuesByUser(@PathVariable Long userId) {
        return issueRepository.findByAssignedUserIdAndDeletedFalse(userId).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/priority/{priority}")
    public List<IssueDTO> getIssuesByPriority(@PathVariable IssuePriority priority) {
        return issueRepository.findByPriorityAndDeletedFalse(priority).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/user/{userId}/priority/{priority}")
    public List<IssueDTO> getIssuesByUserAndPriority(@PathVariable Long userId, @PathVariable IssuePriority priority) {
        return issueRepository.findByAssignedUserIdAndPriorityAndDeletedFalse(userId, priority).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @PutMapping("/{id}/assign/{userId}")
    public Issue assignUser(@PathVariable Long id, @PathVariable Long userId) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        issue.setAssignedUser(user);
        return issueRepository.save(issue);
    }

    @PutMapping("/{id}/status/{status}")
    public Issue changeStatus(@PathVariable Long id, @PathVariable IssueStatus status) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setStatus(status);
        return issueRepository.save(issue);
    }

    @DeleteMapping("/{id}")
    public void softDeleteIssue(@PathVariable Long id) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setDeleted(true);
        issueRepository.save(issue);
    }
}

