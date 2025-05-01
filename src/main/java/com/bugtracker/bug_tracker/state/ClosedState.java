package com.bugtracker.bug_tracker.state;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssueStatus;

public class ClosedState implements IssueState {
    @Override
    public void transitionTo(Issue issue, IssueStatus targetStatus) {
        throw new IllegalStateException("No transitions allowed from CLOSED state.");
    }
}
