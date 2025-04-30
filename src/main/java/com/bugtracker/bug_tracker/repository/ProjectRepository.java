package com.bugtracker.bug_tracker.repository;


import com.bugtracker.bug_tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
