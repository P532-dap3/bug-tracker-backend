package com.bugtracker.bug_tracker.service.impl;

import com.bugtracker.bug_tracker.exception.ResourceNotFoundException;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.service.BaseService;
import com.bugtracker.bug_tracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl extends BaseService<Project> implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    protected void validate(Project project) {
        if (project.getName() == null || project.getName().isBlank()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
    }

    @Override
    protected Project doSave(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }

    @Override
    public Project createProject(Project project) {
        return save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }
}

