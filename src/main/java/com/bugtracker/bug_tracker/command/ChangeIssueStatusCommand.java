package com.bugtracker.bug_tracker.command;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssueStatus;
import com.bugtracker.bug_tracker.state.IssueState;
import com.bugtracker.bug_tracker.state.IssueStateFactory;

public class ChangeIssueStatusCommand implements IssueCommand {

    private final IssueStatus targetStatus;

    public ChangeIssueStatusCommand(IssueStatus targetStatus) {
        this.targetStatus = targetStatus;
    }

    @Override
    public Issue execute(Issue issue) {
        IssueState currentState = IssueStateFactory.getState(issue.getStatus());
        currentState.transitionTo(issue, targetStatus);
        return issue;
    }
}
