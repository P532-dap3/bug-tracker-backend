package com.bugtracker.bug_tracker.state;

import com.bugtracker.bug_tracker.model.IssueStatus;

public class IssueStateFactory {
    public static IssueState getState(IssueStatus currentStatus) {
        return switch (currentStatus) {
            case OPEN -> new OpenState();
            case IN_PROGRESS -> new InProgressState();
            case RESOLVED -> new ResolvedState();
            case CLOSED -> new ClosedState();
        };
    }
}
