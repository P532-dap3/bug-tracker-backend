package com.bugtracker.bug_tracker.Controller;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issue));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(issueService.getIssuesByProjectId(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getById(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssueById(id));
    }
}

