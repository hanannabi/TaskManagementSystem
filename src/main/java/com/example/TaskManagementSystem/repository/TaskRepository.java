package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByPriority(Priority priority);

    List<Task> findByDueDate(LocalDate dueDate);

}
