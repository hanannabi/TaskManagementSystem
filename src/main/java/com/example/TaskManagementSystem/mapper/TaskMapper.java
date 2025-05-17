package com.example.TaskManagementSystem.mapper;

import com.example.TaskManagementSystem.dto.RequestDto;
import com.example.TaskManagementSystem.dto.ResponseDto;

public class TaskMapper {

    public static com.example.TaskManagementSystem.entity.Task toEntity(RequestDto request) {
        return new com.example.TaskManagementSystem.entity.Task(request.getTitle(),
                request.getDescription(),
                request.getDueDate(),
                request.getPriority());
    }

    public static ResponseDto toDto(com.example.TaskManagementSystem.entity.Task savedEntity) {
        return new ResponseDto(savedEntity.getId(),
                savedEntity.getTitle(),
                savedEntity.getDescription(),
                savedEntity.getDueDate(),
                savedEntity.getPriority());
    }
}
