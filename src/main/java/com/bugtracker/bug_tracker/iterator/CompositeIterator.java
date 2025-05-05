package com.bugtracker.bug_tracker.iterator;

import com.bugtracker.bug_tracker.model.Issue;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator<Issue> {
    private Stack<Iterator<Issue>> stack = new Stack<>();

    public CompositeIterator(Issue root) {
        // Start the iteration with the root issue's sub-issues
        stack.push(root.getSubIssues().iterator());
    }

    @Override
    public boolean hasNext() {
        // Check if there is a next issue in the stack
        while (!stack.isEmpty()) {
            Iterator<Issue> currentIterator = stack.peek();
            if (currentIterator.hasNext()) {
                return true;
            } else {
                stack.pop();  // Remove exhausted iterator
            }
        }
        return false;
    }

    @Override
    public Issue next() {
        if (hasNext()) {
            Iterator<Issue> currentIterator = stack.peek();
            Issue currentIssue = currentIterator.next();

            // Push the sub-issues of the current issue onto the stack for future iteration
            if (currentIssue.getSubIssues() != null && !currentIssue.getSubIssues().isEmpty()) {
                stack.push(currentIssue.getSubIssues().iterator());
            }
            return currentIssue;
        }
        throw new IllegalStateException("No more elements");
    }
}

