package com.bugtracker.bug_tracker.command;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.User;

public class AssignUserCommand implements IssueCommand {
    private final User user;

    public AssignUserCommand(User user) {
        this.user = user;
    }

    @Override
    public Issue execute(Issue issue) {
        issue.setAssignedUser(user);
        return issue;
    }
}
