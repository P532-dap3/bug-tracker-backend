package com.bugtracker.bug_tracker.service;

import com.bugtracker.bug_tracker.command.*;
import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.*;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bugtracker.bug_tracker.iterator.CompositeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    public Issue getIssueById(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    public Issue updateIssue(Long issueId, IssueDTO dto) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setTitle(dto.title);
        issue.setDescription(dto.description);
        issue.setPriority(dto.priority);
        issue.setStatus(dto.status);

        if (dto.assignedUserId != null) {
            User user = userRepository.findById(dto.assignedUserId)
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            issue.setAssignedUser(user);
        } else {
            issue.setAssignedUser(null);
        }

        return issueRepository.save(issue);
    }


    public Issue createIssueFromDTO(IssueDTO dto) {
        Project project = projectRepository.findById(dto.projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = null;
        if (dto.assignedUserId != null) {
            user = userRepository.findById(dto.assignedUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Issue parentIssue = null;
        if (dto.parentIssueId != null) {
            parentIssue = issueRepository.findById(dto.parentIssueId)
                    .orElseThrow(() -> new RuntimeException("Parent issue not found"));
            if (parentIssue.getParentIssue() != null) {
                throw new RuntimeException("Cannot create a sub-issue from a sub-issue");
            }
        }

        Issue issue = DTOMapper.toIssueEntity(dto, project, user, parentIssue);
        issue.setParentIssue(parentIssue);

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

    public List<Issue> getSubIssues(Long parentIssueId) {
        Issue parent = issueRepository.findById(parentIssueId)
                .orElseThrow(() -> new RuntimeException("Parent issue not found"));
        return parent.getSubIssues();
    }

    public Issue changeStatus(Long issueId, IssueStatus targetStatus) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueCommand command = new ChangeIssueStatusCommand(targetStatus);
        IssueCommandInvoker invoker = new IssueCommandInvoker();
        Issue updatedIssue = invoker.runCommand(command, issue);

        return issueRepository.save(updatedIssue);
    }

    public Issue assignUser(Long issueId, Long userId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        IssueCommand command = new AssignUserCommand(user);
        Issue updated = new IssueCommandInvoker().runCommand(command, issue);

        return issueRepository.save(updated);
    }

    public Issue changePriority(Long issueId, IssuePriority priority) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueCommand command = new ChangeIssuePriorityCommand(priority);
        Issue updated = new IssueCommandInvoker().runCommand(command, issue);

        return issueRepository.save(updated);
    }

    public List<IssueDTO> getIssueHierarchy(Long issueId) {
        Issue rootIssue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        List<IssueDTO> issueDTOs = new ArrayList<>();
        CompositeIterator iterator = new CompositeIterator(rootIssue);

        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            issueDTOs.add(DTOMapper.toIssueDTO(issue));
        }

        return issueDTOs;
    }

    public void deleteIssueAndSubIssues(Long issueId) {
        Issue rootIssue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        List<Issue> issuesToDelete = new ArrayList<>();
        CompositeIterator iterator = new CompositeIterator(rootIssue);

        while (iterator.hasNext()) {
            issuesToDelete.add(iterator.next());
        }

        // Delete sub-issues first, then the parent
        for (int i = issuesToDelete.size() - 1; i >= 0; i--) {
            issueRepository.delete(issuesToDelete.get(i));
        }
    }
}
