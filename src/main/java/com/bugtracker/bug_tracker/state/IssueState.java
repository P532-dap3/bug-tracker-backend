package com.bugtracker.bug_tracker.state;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssueStatus;

public interface IssueState {
    void transitionTo(Issue issue, IssueStatus targetStatus);
}
