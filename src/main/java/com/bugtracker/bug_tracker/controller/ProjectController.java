package com.bugtracker.bug_tracker.controller;

import com.bugtracker.bug_tracker.dto.ProjectDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(DTOMapper::toProjectDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = DTOMapper.toProjectEntity(projectDTO);
        Project saved = projectRepository.save(project);
        return DTOMapper.toProjectDTO(saved);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return DTOMapper.toProjectDTO(project);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(dto.name);
        project.setDescription(dto.description);

        Project updated = projectRepository.save(project);
        return DTOMapper.toProjectDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
    }
}

