package com.bugtracker.bug_tracker.Controller;

import com.bugtracker.bug_tracker.controller.IssueController;
import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.model.Issue;
import com.bugtracker.bug_tracker.model.IssuePriority;
import com.bugtracker.bug_tracker.model.IssueStatus;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import com.bugtracker.bug_tracker.service.IssueService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IssueController.class)
public class IssueControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IssueService issueService;

    private ObjectMapper objectMapper;

    private IssueDTO sampleIssueDTO;
    private Issue sampleIssue;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public IssueService issueService() {
            return Mockito.mock(IssueService.class);
        }

        @Bean
        public IssueRepository issueRepository() {
            return Mockito.mock(IssueRepository.class);
        }

        @Bean
        public ProjectRepository projectRepository() {
            return Mockito.mock(ProjectRepository.class);
        }

        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }
    }

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        sampleIssueDTO = new IssueDTO();
        sampleIssueDTO.id = 1L;
        sampleIssueDTO.title = "Sample Issue";
        sampleIssueDTO.description = "Sample issue description";
        sampleIssueDTO.status = IssueStatus.OPEN;
        sampleIssueDTO.priority = IssuePriority.MEDIUM;

        sampleIssue = new Issue();
        sampleIssue.setId(1L);
        sampleIssue.setTitle("Sample Issue");
        sampleIssue.setDescription("Sample issue description");
        sampleIssue.setStatus(IssueStatus.OPEN);
        sampleIssue.setPriority(IssuePriority.MEDIUM);
    }

    @Test
    public void testGetAllIssues() throws Exception {
        Mockito.when(issueService.getAllActiveIssues()).thenReturn(List.of());

        mockMvc.perform(get("/api/issues"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssueById() throws Exception {
        Mockito.when(issueService.getIssueById(1L)).thenReturn(sampleIssue);

        mockMvc.perform(get("/api/issues/1"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testUpdateIssue() throws Exception {
//        Mockito.when(issueService.updateIssue(eq(1L), any(Issue.class))).thenReturn(sampleIssue);
//
//        mockMvc.perform(put("/api/issues/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sampleIssueDTO)))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testGetIssuesByStatus() throws Exception {
        Mockito.when(issueService.getIssuesByStatus(IssueStatus.OPEN)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/status/OPEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssueHierarchy() throws Exception {
        Mockito.when(issueService.getIssueHierarchy(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/1/hierarchy"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateIssue() throws Exception {
        Mockito.when(issueService.createIssueFromDTO(any(IssueDTO.class))).thenReturn(sampleIssue);

        mockMvc.perform(post("/api/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleIssueDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssuesByProject() throws Exception {
        Mockito.when(issueService.getIssuesByProject(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/project/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssuesByProjectAndStatus() throws Exception {
        Mockito.when(issueService.getIssuesByProjectAndStatus(1L, IssueStatus.OPEN)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/project/1/status/OPEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssuesByUser() throws Exception {
        Mockito.when(issueService.getIssuesByUser(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssuesByPriority() throws Exception {
        Mockito.when(issueService.getIssuesByPriority(IssuePriority.MEDIUM)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/priority/MEDIUM"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIssuesByUserAndPriority() throws Exception {
        Mockito.when(issueService.getIssuesByUserAndPriority(1L, IssuePriority.MEDIUM)).thenReturn(List.of());

        mockMvc.perform(get("/api/issues/user/1/priority/MEDIUM"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAssignUser() throws Exception {
        Mockito.when(issueService.assignUser(1L, 2L)).thenReturn(sampleIssue);

        mockMvc.perform(put("/api/issues/1/assign/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangeStatus() throws Exception {
        Mockito.when(issueService.changeStatus(1L, IssueStatus.RESOLVED)).thenReturn(sampleIssue);

        mockMvc.perform(put("/api/issues/1/status/RESOLVED"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePriority() throws Exception {
        Mockito.when(issueService.changePriority(1L, IssuePriority.HIGH)).thenReturn(sampleIssue);

        mockMvc.perform(put("/api/issues/1/priority/HIGH"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteIssue() throws Exception {
        Mockito.doNothing().when(issueService).deleteIssueAndSubIssues(1L);

        mockMvc.perform(delete("/api/issues/1"))
                .andExpect(status().isOk());
    }
}



