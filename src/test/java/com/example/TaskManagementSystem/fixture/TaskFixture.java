package com.example.TaskManagementSystem.fixture;

import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.enums.Priority;

import java.time.LocalDate;

public class TaskFixture {
    public static Task getTask() {
        return new Task(1, "Test Task", "Test Description",
                LocalDate.of(2025, 05, 20), Priority.P0, false);
    }
}
