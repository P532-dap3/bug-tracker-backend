package com.bugtracker.bug_tracker.service;

import com.bugtracker.bug_tracker.model.Issue;

import java.util.List;

public interface IssueService {
    Issue createIssue(Issue issue);
    List<Issue> getIssuesByProjectId(Long projectId);
    Issue getIssueById(Long id);
}
