package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.RequestDto;
import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Task create(@Valid @RequestBody Task task) {
        return taskService.create(task);
    }

    @GetMapping()
    public List<com.example.TaskManagementSystem.entity.Task> getAllTasksSorted(@RequestParam(defaultValue = "dueDate") String sortBy,
                                                                                @RequestParam(defaultValue = "asc") String direction) {
        return taskService.getAllTaskSorted(sortBy, direction);
    }


    @GetMapping("getById/{id}")
    public ResponseDto getById(@PathVariable int id) {
        return taskService.getById(id);
    }
}
