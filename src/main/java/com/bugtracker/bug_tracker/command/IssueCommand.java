package com.bugtracker.bug_tracker.command;

import com.bugtracker.bug_tracker.model.Issue;

public interface IssueCommand {
    Issue execute(Issue issue);
}
