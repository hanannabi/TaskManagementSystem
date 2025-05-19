package com.example.TaskManagementSystem.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(int id) {
        super("Task with id " + id + " not found");
    }
}
