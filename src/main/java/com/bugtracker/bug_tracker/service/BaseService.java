package com.bugtracker.bug_tracker.service;

public abstract class BaseService<T> {

    public T save(T entity) {
        validate(entity);
        return doSave(entity);
    }

    protected abstract void validate(T entity);
    protected abstract T doSave(T entity);
}
