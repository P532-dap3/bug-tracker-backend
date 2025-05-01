package com.bugtracker.bug_tracker.controller;

import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssuePriority;
import com.bugtracker.bug_tracker.model.IssueStatus;
import com.bugtracker.bug_tracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public List<IssueDTO> getAllIssues() {
        return issueService.getAllActiveIssues().stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/status/{status}")
    public List<IssueDTO> getByStatus(@PathVariable IssueStatus status) {
        return issueService.getIssuesByStatus(status).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @PostMapping
    public IssueDTO createIssue(@RequestBody IssueDTO dto) {
        Issue issue = issueService.createIssueFromDTO(dto);
        return DTOMapper.toIssueDTO(issue);
    }

    @GetMapping("/project/{projectId}")
    public List<IssueDTO> getIssuesByProject(@PathVariable Long projectId) {
        return issueService.getIssuesByProject(projectId).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/project/{projectId}/status/{status}")
    public List<IssueDTO> getIssuesByProjectAndStatus(@PathVariable Long projectId, @PathVariable IssueStatus status) {
        return issueService.getIssuesByProjectAndStatus(projectId, status).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<IssueDTO> getIssuesByUser(@PathVariable Long userId) {
        return issueService.getIssuesByUser(userId).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/priority/{priority}")
    public List<IssueDTO> getIssuesByPriority(@PathVariable IssuePriority priority) {
        return issueService.getIssuesByPriority(priority).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @GetMapping("/user/{userId}/priority/{priority}")
    public List<IssueDTO> getIssuesByUserAndPriority(@PathVariable Long userId, @PathVariable IssuePriority priority) {
        return issueService.getIssuesByUserAndPriority(userId, priority).stream()
                .map(DTOMapper::toIssueDTO)
                .toList();
    }

    @PutMapping("/{id}/assign/{userId}")
    public IssueDTO assignUser(@PathVariable Long id, @PathVariable Long userId) {
        return DTOMapper.toIssueDTO(issueService.assignUser(id, userId));
    }

    @PutMapping("/{id}/status/{status}")
    public IssueDTO changeStatus(@PathVariable Long id, @PathVariable IssueStatus status) {
        return DTOMapper.toIssueDTO(issueService.changeStatus(id, status));
    }

    @PutMapping("/{id}/priority/{priority}")
    public IssueDTO changePriority(@PathVariable Long id, @PathVariable IssuePriority priority) {
        return DTOMapper.toIssueDTO(issueService.changePriority(id, priority));
    }

    @DeleteMapping("/{id}")
    public void softDeleteIssue(@PathVariable Long id) {
        issueService.softDelete(id);
    }
}
