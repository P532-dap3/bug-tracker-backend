package com.bugtracker.bug_tracker.Repository;

import com.bugtracker.bug_tracker.model.Project;
import com.bugtracker.bug_tracker.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProjectRepositoryUnitTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("Save and find a project by ID")
    public void testSaveAndFindProject() {
        // Given
        Project project = new Project();
        project.setName("Bug Tracker");
        project.setDescription("A system to track software bugs");

        // When
        Project savedProject = projectRepository.save(project);

        // Then
        Optional<Project> found = projectRepository.findById(savedProject.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Bug Tracker");
        assertThat(found.get().getDescription()).isEqualTo("A system to track software bugs");
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Delete a project")
    public void testDeleteProject() {
        // Given
        Project project = new Project();
        project.setName("To Be Deleted");
        project.setDescription("Will be removed");
        Project saved = projectRepository.save(project);

        // When
        projectRepository.delete(saved);

        // Then
        Optional<Project> found = projectRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }

    @Test
    @DisplayName("Find all projects")
    public void testFindAllProjects() {
        // Given
        Project p1 = new Project();
        p1.setName("Project 1");
        p1.setDescription("First project");

        Project p2 = new Project();
        p2.setName("Project 2");
        p2.setDescription("Second project");

        projectRepository.save(p1);
        projectRepository.save(p2);

        // When
        var allProjects = projectRepository.findAll();

        // Then
        assertThat(allProjects).hasSize(2);
        assertThat(allProjects).extracting(Project::getName).contains("Project 1", "Project 2");
    }
}

