package com.bugtracker.bug_tracker.state;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssueStatus;

public class ResolvedState implements IssueState {
    @Override
    public void transitionTo(Issue issue, IssueStatus targetStatus) {
        switch (targetStatus) {
            case CLOSED, IN_PROGRESS -> issue.setStatus(targetStatus);
            default -> throw new IllegalStateException("Invalid transition from RESOLVED to " + targetStatus);
        }
    }
}
