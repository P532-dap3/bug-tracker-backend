package com.bugtracker.bug_tracker.Repository;

import com.bugtracker.bug_tracker.model.*;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import com.bugtracker.bug_tracker.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IssueRepositoryUnitTests {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private static Project testProject;
    private static User testUser;

    @BeforeAll
    static void setup(@Autowired ProjectRepository projectRepo, @Autowired UserRepository userRepo) {
        testProject = new Project();
        testProject.setName("Test Project");
        testProject = projectRepo.save(testProject);

        testUser = new User();
        testUser.setUsername("John Doe");
        testUser.setEmail("john.doe@example.com");
        testUser = userRepo.save(testUser);
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveIssueTest() {
        Issue issue = new Issue();
        issue.setTitle("Test Issue");
        issue.setDescription("This is a test issue.");
        issue.setStatus(IssueStatus.OPEN);
        issue.setPriority(IssuePriority.HIGH);
        issue.setProject(testProject);
        issue.setAssignedUser(testUser);

        Issue savedIssue = issueRepository.save(issue);
        System.out.println(savedIssue);
        Assertions.assertThat(savedIssue.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getIssueTest() {
        Issue issue = issueRepository.findById(1L).get();
        Assertions.assertThat(issue.getTitle()).isEqualTo("Test Issue");
    }

    @Test
    @Order(3)
    public void findByStatusTest() {
        List<Issue> openIssues = issueRepository.findByStatus(IssueStatus.OPEN);
        Assertions.assertThat(openIssues).isNotEmpty();
    }

    @Test
    @Order(4)
    public void findByProjectTest() {
        List<Issue> projectIssues = issueRepository.findByProject(testProject);
        Assertions.assertThat(projectIssues).isNotEmpty();
    }

    @Test
    @Order(5)
    public void findByAssignedUserTest() {
        List<Issue> userIssues = issueRepository.findByAssignedUser(testUser);
        Assertions.assertThat(userIssues).isNotEmpty();
    }

    @Test
    @Order(6)
    public void findByProjectIdAndStatusAndDeletedFalseTest() {
        List<Issue> issues = issueRepository.findByProjectIdAndStatusAndDeletedFalse(
                testProject.getId(), IssueStatus.OPEN);
        Assertions.assertThat(issues).isNotEmpty();
    }

    @Test
    @Order(7)
    public void updateIssueTest() {
        Issue issue = issueRepository.findById(1L).get();
        issue.setPriority(IssuePriority.MEDIUM);
        Issue updated = issueRepository.save(issue);
        Assertions.assertThat(updated.getPriority()).isEqualTo(IssuePriority.MEDIUM);
    }

    @Test
    @Order(8)
    public void deleteIssueTest() {
        Issue issue = issueRepository.findById(1L).get();
        issueRepository.delete(issue);
        Optional<Issue> deleted = issueRepository.findById(1L);
        Assertions.assertThat(deleted).isEmpty();
    }
}

