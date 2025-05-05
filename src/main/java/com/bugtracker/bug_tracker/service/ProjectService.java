package com.bugtracker.bug_tracker.service;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updated) {
        Project existing = getProject(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return projectRepository.save(existing);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Delete all issues (and their sub-issues) for this project
        List<Issue> issues = issueRepository.findByProjectId(projectId);
        issueRepository.deleteAll(issues);

        // Finally delete the project
        projectRepository.delete(project);
    }
}

