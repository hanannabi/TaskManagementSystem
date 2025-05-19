package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.dto.UpdateRequestDto;
import com.example.TaskManagementSystem.dto.UpdateResponseDto;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.enums.Priority;
import com.example.TaskManagementSystem.exception.TaskNotFoundException;
import com.example.TaskManagementSystem.mapper.TaskMapper;
import com.example.TaskManagementSystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task create(Task task) {
        System.out.println("In TaskService");
        return taskRepository.save(task);
    }

    public List<Task> getAllTaskSorted(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        return taskRepository.findAll(sort);
    }

    public ResponseDto getById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskMapper.toDto(task);
    }

    public UpdateResponseDto update(UpdateRequestDto updateRequestDto, int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(updateRequestDto.getTitle());
        task.setDueDate(updateRequestDto.getDueDate());
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toUpdateDto(savedTask);
    }

    public String delete(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
        return "Task delete successful congrats";
    }

    public Task updateCompletionStatus(int id, boolean completed) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setCompleted(completed);
        return taskRepository.save(task);
    }


    public List<Task> filterByCompleted(Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> filterByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> filterByDueDate(LocalDate dueDate) {
        return taskRepository.findByDueDate(dueDate);
    }
}
