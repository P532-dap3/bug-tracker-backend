package com.bugtracker.bug_tracker.repository;


import com.bugtracker.bug_tracker.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByProject(Project project);

    List<Issue> findByAssignedUser(User user);

    List<Issue> findByProjectId(Long projectId);

    List<Issue> findByDeletedFalse();

    List<Issue> findByStatusAndDeletedFalse(IssueStatus status);
    List<Issue> findByProjectIdAndDeletedFalse(Long projectId);
    List<Issue> findByProjectIdAndStatusAndDeletedFalse(Long projectId, IssueStatus status);

    List<Issue> findByAssignedUserIdAndDeletedFalse(Long userId);
    List<Issue> findByPriorityAndDeletedFalse(IssuePriority priority);
    List<Issue> findByAssignedUserIdAndPriorityAndDeletedFalse(Long userId, IssuePriority priority);
}

