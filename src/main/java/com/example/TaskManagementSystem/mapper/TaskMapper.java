package com.example.TaskManagementSystem.mapper;

import com.example.TaskManagementSystem.dto.TaskCreateRequest;
import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.entity.Task;

public class TaskMapper {

    public static Task toEntity(TaskCreateRequest request) {
        return new Task(request.getTitle(),
                request.getDescription(),
                request.getDueDate(),
                request.getPriority());
    }

    public static ResponseDto toDto(Task savedEntity) {
        return new ResponseDto(savedEntity.getId(),
                savedEntity.getTitle(),
                savedEntity.getDescription(),
                savedEntity.getDueDate(),
                savedEntity.getPriority());
    }
}
