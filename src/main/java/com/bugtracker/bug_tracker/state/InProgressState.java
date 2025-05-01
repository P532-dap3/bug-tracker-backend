package com.bugtracker.bug_tracker.state;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssueStatus;

public class InProgressState implements IssueState {
    @Override
    public void transitionTo(Issue issue, IssueStatus targetStatus) {
        switch (targetStatus) {
            case RESOLVED, OPEN -> issue.setStatus(targetStatus);
            default -> throw new IllegalStateException("Invalid transition from IN_PROGRESS to " + targetStatus);
        }
    }
}
