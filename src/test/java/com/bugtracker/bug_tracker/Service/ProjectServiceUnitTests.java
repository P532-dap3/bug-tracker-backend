package com.bugtracker.bug_tracker.Service;

import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceUnitTests {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project sampleProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProject = new Project();
        sampleProject.setId(1L);
        sampleProject.setName("Bug Tracker");
        sampleProject.setDescription("Track bugs");
    }

    @Test
    void testGetAllProjects() {
        List<Project> projects = List.of(sampleProject);
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertEquals(1, result.size());
        assertEquals("Bug Tracker", result.get(0).getName());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProject_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        Project result = projectService.getProject(1L);

        assertNotNull(result);
        assertEquals("Bug Tracker", result.getName());
    }

    @Test
    void testGetProject_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectService.getProject(1L));
        assertEquals("Project not found", exception.getMessage());
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        Project created = projectService.createProject(sampleProject);

        assertNotNull(created);
        verify(projectRepository, times(1)).save(sampleProject);
    }

    @Test
    void testUpdateProject() {
        Project updated = new Project();
        updated.setName("Updated Project");
        updated.setDescription("Updated Description");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        Project result = projectService.updateProject(1L, updated);

        assertEquals("Updated Project", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(projectRepository).save(sampleProject);
    }

    @Test
    void testDeleteProject() {
        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        List<Issue> issueList = List.of(issue1, issue2);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        when(issueRepository.findByProjectId(1L)).thenReturn(issueList);

        projectService.deleteProject(1L);

        verify(issueRepository).deleteAll(issueList);
        verify(projectRepository).delete(sampleProject);
    }

    @Test
    void testDeleteProject_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectService.deleteProject(1L));
        assertEquals("Project not found", exception.getMessage());
    }
}

