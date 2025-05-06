package com.bugtracker.bug_tracker.iterator;

import com.bugtracker.bug_tracker.model.Issue;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator<Issue> {
    private final Stack<Iterator<Issue>> stack = new Stack<>();
    private Issue root;
    private boolean rootReturned = false;

    public CompositeIterator(Issue root) {
        this.root = root;
        if (root.getSubIssues() != null && !root.getSubIssues().isEmpty()) {
            stack.push(root.getSubIssues().iterator());
        }
    }

    @Override
    public boolean hasNext() {
        return !rootReturned || !stack.isEmpty() && stack.peek().hasNext();
    }

    @Override
    public Issue next() {
        if (!rootReturned) {
            rootReturned = true;
            return root;
        }

        while (!stack.isEmpty()) {
            Iterator<Issue> currentIterator = stack.peek();
            if (currentIterator.hasNext()) {
                Issue currentIssue = currentIterator.next();
                if (currentIssue.getSubIssues() != null && !currentIssue.getSubIssues().isEmpty()) {
                    stack.push(currentIssue.getSubIssues().iterator());
                }
                return currentIssue;
            } else {
                stack.pop();
            }
        }

        throw new IllegalStateException("No more elements");
    }
}


