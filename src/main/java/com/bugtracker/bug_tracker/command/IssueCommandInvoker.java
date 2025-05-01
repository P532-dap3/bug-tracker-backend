package com.bugtracker.bug_tracker.command;

import com.bugtracker.bug_tracker.model.Issue;

public class IssueCommandInvoker {

    public Issue runCommand(IssueCommand command, Issue issue) {
        return command.execute(issue);
    }
}
