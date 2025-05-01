package com.bugtracker.bug_tracker.state;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssueStatus;

public class OpenState implements IssueState {
    @Override
    public void transitionTo(Issue issue, IssueStatus targetStatus) {
        switch (targetStatus) {
            case IN_PROGRESS, CLOSED -> issue.setStatus(targetStatus);
            default -> throw new IllegalStateException("Invalid transition from OPEN to " + targetStatus);
        }
    }
}
