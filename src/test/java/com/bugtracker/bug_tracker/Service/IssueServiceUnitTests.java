package com.bugtracker.bug_tracker.Service;

import com.bugtracker.bug_tracker.dto.IssueDTO;
import com.bugtracker.bug_tracker.model.*;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import com.bugtracker.bug_tracker.mapper.DTOMapper;
import com.bugtracker.bug_tracker.service.IssueService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class IssueServiceUnitTests {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IssueService issueService;

    private Issue issue;
    private IssueDTO issueDTO;
    private Project project;
    private User user;

    @BeforeEach
    public void setup() {
        // Mocking dependencies
        project = new Project();
        project.setId(1L);
        project.setName("BugTracker");

        user = new User();
        user.setId(1L);
        user.setUsername("devuser");

        issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Login Bug");
        issue.setDescription("Login fails for valid user");
        issue.setStatus(IssueStatus.OPEN);
        issue.setPriority(IssuePriority.HIGH);
        issue.setProject(project);
        issue.setAssignedUser(user);

        issueDTO = new IssueDTO();
        issueDTO.setTitle("Updated Login Bug");
        issueDTO.setDescription("Login now works");
        issueDTO.setStatus(IssueStatus.RESOLVED);
        issueDTO.setPriority(IssuePriority.MEDIUM);
        issueDTO.setAssignedUserId(1L);
        issueDTO.setProjectId(1L);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(projectRepository.findById(1L)).willReturn(Optional.of(project));
    }

    @Test
    @Order(1)
    public void getAllActiveIssuesTest() {
        // Given
        given(issueRepository.findByDeletedFalse()).willReturn(List.of(issue));

        // When
        List<Issue> activeIssues = issueService.getAllActiveIssues();

        // Then
        assertThat(activeIssues).isNotNull();
        assertThat(activeIssues.size()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getIssuesByStatusTest() {
        // Given
        given(issueRepository.findByStatusAndDeletedFalse(IssueStatus.OPEN)).willReturn(List.of(issue));

        // When
        List<Issue> issues = issueService.getIssuesByStatus(IssueStatus.OPEN);

        // Then
        assertThat(issues).isNotNull();
        assertThat(issues.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void getIssueByIdTest() {
        // Given
        given(issueRepository.findById(1L)).willReturn(Optional.of(issue));

        // When
        Issue fetchedIssue = issueService.getIssueById(1L);

        // Then
        assertThat(fetchedIssue).isNotNull();
        assertThat(fetchedIssue.getId()).isEqualTo(1L);
    }

    @Test
    @Order(4)
    public void updateIssueTest() {
        // Given
        given(issueRepository.findById(1L)).willReturn(Optional.of(issue));
        given(issueRepository.save(any(Issue.class))).willReturn(issue);

        // When
        Issue updatedIssue = issueService.updateIssue(1L, issueDTO);

        // Then
        assertThat(updatedIssue).isNotNull();
        assertThat(updatedIssue.getTitle()).isEqualTo("Updated Login Bug");
        assertThat(updatedIssue.getDescription()).isEqualTo("Login now works");
        assertThat(updatedIssue.getStatus()).isEqualTo(IssueStatus.RESOLVED);
    }

    @Test
    @Order(5)
    public void createIssueFromDTOTest() {
        // Given
        given(projectRepository.findById(1L)).willReturn(Optional.of(project));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(issueRepository.save(any(Issue.class)))
                .willAnswer(invocation -> invocation.getArgument(0));  // return what was saved

        // When
        Issue createdIssue = issueService.createIssueFromDTO(issueDTO);

        // Then
        assertThat(createdIssue).isNotNull();
        assertThat(createdIssue.getTitle()).isEqualTo("Updated Login Bug");
        assertThat(createdIssue.getAssignedUser()).isNotNull();
    }

    @Test
    @Order(6)
    public void getIssuesByProjectTest() {
        // Given
        given(issueRepository.findByProjectIdAndDeletedFalse(1L)).willReturn(List.of(issue));

        // When
        List<Issue> issues = issueService.getIssuesByProject(1L);

        // Then
        assertThat(issues).isNotNull();
        assertThat(issues.size()).isGreaterThan(0);
    }

    @Test
    @Order(7)
    public void getIssuesByUserTest() {
        // Given
        given(issueRepository.findByAssignedUserIdAndDeletedFalse(1L)).willReturn(List.of(issue));

        // When
        List<Issue> issues = issueService.getIssuesByUser(1L);

        // Then
        assertThat(issues).isNotNull();
        assertThat(issues.size()).isGreaterThan(0);
    }

    @Test
    @Order(8)
    public void changeStatusTest() {
        // Given
        given(issueRepository.findById(1L)).willReturn(Optional.of(issue));
        given(issueRepository.save(any(Issue.class))).willReturn(issue);

        // When
        Issue updatedIssue = issueService.changeStatus(1L, IssueStatus.CLOSED);

        // Then
        assertThat(updatedIssue).isNotNull();
        assertThat(updatedIssue.getStatus()).isEqualTo(IssueStatus.CLOSED);
    }

    @Test
    @Order(9)
    public void assignUserTest() {
        // Given
        given(issueRepository.findById(1L)).willReturn(Optional.of(issue));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(issueRepository.save(any(Issue.class))).willReturn(issue);

        // When
        Issue updatedIssue = issueService.assignUser(1L, 1L);

        // Then
        assertThat(updatedIssue).isNotNull();
        assertThat(updatedIssue.getAssignedUser()).isNotNull();
    }

    @Test
    @Order(10)
    public void getIssueHierarchyTest() {
        // Given
        given(issueRepository.findById(1L)).willReturn(Optional.of(issue));

        // When
        List<IssueDTO> issueHierarchy = issueService.getIssueHierarchy(1L);

        // Then
        assertThat(issueHierarchy).isNotNull();
        assertThat(issueHierarchy.size()).isGreaterThan(0);
    }

    @Test
    @Order(11)
    public void deleteIssueAndSubIssuesTest() {
        // Given
        List<Issue> issuesToDelete = List.of(issue);
        given(issueRepository.findById(1L)).willReturn(Optional.of(issue));
        willDoNothing().given(issueRepository).delete(any(Issue.class));

        // When
        issueService.deleteIssueAndSubIssues(1L);

        // Then
        verify(issueRepository, times(1)).delete(issue);
    }
}

