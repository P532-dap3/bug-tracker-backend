package com.bugtracker.bug_tracker.controller;

import com.bugtracker.bug_tracker.dto.ProjectDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects().stream()
                .map(DTOMapper::toProjectDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = DTOMapper.toProjectEntity(projectDTO);
        Project saved = projectService.createProject(project);
        return DTOMapper.toProjectDTO(saved);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        return DTOMapper.toProjectDTO(project);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        Project updated = new Project();
        updated.setName(dto.name);
        updated.setDescription(dto.description);

        Project saved = projectService.updateProject(id, updated);
        return DTOMapper.toProjectDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
