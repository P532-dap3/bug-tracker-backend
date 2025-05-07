package com.bugtracker.bug_tracker.Controller;

import com.bugtracker.bug_tracker.controller.ProjectController;
import com.bugtracker.bug_tracker.dto.ProjectDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import com.bugtracker.bug_tracker.service.IssueService;
import com.bugtracker.bug_tracker.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;


    @Autowired
    private ObjectMapper objectMapper;

    private Project sampleProject;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProjectService projectService() {
            return Mockito.mock(ProjectService.class);
        }

        @Bean
        public IssueRepository issueRepository() {
            return Mockito.mock(IssueRepository.class);
        }

        @Bean
        public ProjectRepository projectRepository() {
            return Mockito.mock(ProjectRepository.class);
        }


    }

    @BeforeEach
    public void setup() {
        sampleProject = new Project();
        sampleProject.setId(1L);
        sampleProject.setName("Test Project");
        sampleProject.setDescription("Description");
        sampleProject.setCreatedAt(LocalDateTime.now());
        sampleProject.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<Project> projects = Arrays.asList(sampleProject);
        Mockito.when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Test Project"));
    }

    @Test
    public void testCreateProject() throws Exception {
        ProjectDTO dto = DTOMapper.toProjectDTO(sampleProject);
        Project projectInput = DTOMapper.toProjectEntity(dto);

        Mockito.when(projectService.createProject(any(Project.class))).thenReturn(sampleProject);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    public void testGetProjectById() throws Exception {
        Mockito.when(projectService.getProject(1L)).thenReturn(sampleProject);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    public void testUpdateProject() throws Exception {
        Project updated = new Project();
        updated.setName("Updated Name");
        updated.setDescription("Updated Desc");

        Project saved = new Project();
        saved.setId(1L);
        saved.setName("Updated Name");
        saved.setDescription("Updated Desc");

        Mockito.when(projectService.updateProject(eq(1L), any(Project.class)))
                .thenReturn(saved);

        ProjectDTO updateDTO = new ProjectDTO();
        updateDTO.name = "Updated Name";
        updateDTO.description = "Updated Desc";

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.description").value("Updated Desc"));
    }

    @Test
    public void testDeleteProject() throws Exception {
        Mockito.doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk());
    }
}

