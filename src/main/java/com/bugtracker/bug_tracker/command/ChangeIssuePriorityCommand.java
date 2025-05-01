package com.bugtracker.bug_tracker.command;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssuePriority;

public class ChangeIssuePriorityCommand implements IssueCommand {
    private final IssuePriority newPriority;

    public ChangeIssuePriorityCommand(IssuePriority newPriority) {
        this.newPriority = newPriority;
    }

    @Override
    public Issue execute(Issue issue) {
        issue.setPriority(newPriority);
        return issue;
    }
}
