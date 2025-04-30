package com.bugtracker.bug_tracker.service;

import com.bugtracker.bug_tracker.model.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(Long id);
}
