package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.dto.RequestDto;
import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.dto.UpdateRequestDto;
import com.example.TaskManagementSystem.dto.UpdateResponseDto;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.exception.TaskNotFoundException;
import com.example.TaskManagementSystem.mapper.TaskMapper;
import com.example.TaskManagementSystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public ResponseDto create(RequestDto request) {
        Task entity = TaskMapper.toEntity(request);
        Task savedEntity = taskRepository.save(entity);
        return TaskMapper.toDto(savedEntity);
    }

    public List<com.example.TaskManagementSystem.entity.Task> getAllTaskSorted(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        return taskRepository.findAll(sort);
    }

    public ResponseDto getById(int id) {
        com.example.TaskManagementSystem.entity.Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("RequestDto Not found"));
        return TaskMapper.toDto(task);
    }

    public UpdateResponseDto update(UpdateRequestDto updateRequestDto, int id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("No Task with the id " + id + " Found"));
        task.setTitle(updateRequestDto.getTitle());
        task.setDueDate(updateRequestDto.getDueDate());
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toUpdateDto(savedTask);
    }

    public String delete(int id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
        return "Task delete successful";
    }
}
