package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.TaskCreateRequest;
import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.dto.UpdateRequestDto;
import com.example.TaskManagementSystem.dto.UpdateResponseDto;
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
    public ResponseDto create(@Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        return taskService.create(taskCreateRequest);
    }

    @GetMapping()
    public List<Task> getAllTasksSorted(@RequestParam(defaultValue = "dueDate") String sortBy,
                                        @RequestParam(defaultValue = "asc") String direction) {
        return taskService.getAllTaskSorted(sortBy, direction);
    }


    @GetMapping("getById/{id}")
    public ResponseDto getById(@PathVariable int id) {
        return taskService.getById(id);
    }


    @PutMapping("/{id}")
    public UpdateResponseDto update(@RequestBody UpdateRequestDto updateRequestDto, @PathVariable int id) {
        return taskService.update(updateRequestDto,id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){
       return taskService.delete(id);
    }
}
