package com.bugtracker.bug_tracker.service.impl;

import com.bugtracker.bug_tracker.exception.ResourceNotFoundException;
import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.service.BaseService;
import com.bugtracker.bug_tracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueServiceImpl extends BaseService<Issue> implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Override
    protected void validate(Issue issue) {
        if (issue.getTitle() == null || issue.getTitle().isBlank()) {
            throw new IllegalArgumentException("Issue title cannot be empty");
        }
    }

    @Override
    protected Issue doSave(Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    @Override
    public Issue createIssue(Issue issue) {
        return save(issue);
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
    }
}

