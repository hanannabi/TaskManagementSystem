package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.dto.UpdateRequestDto;
import com.example.TaskManagementSystem.dto.UpdateResponseDto;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.enums.Priority;
import com.example.TaskManagementSystem.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<Task> getAllTasksSorted(@RequestParam(defaultValue = "dueDate") String sortBy,
                                        @RequestParam(defaultValue = "asc") String direction) {
        return taskService.getAllTaskSorted(sortBy, direction);
    }


    @GetMapping("getById/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDto getById(@PathVariable int id) {
        return taskService.getById(id);
    }


    @PutMapping("/{id}")
    public UpdateResponseDto update(@RequestBody UpdateRequestDto updateRequestDto, @PathVariable int id) {
        return taskService.update(updateRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable int id) {
        return taskService.delete(id);
    }

    @PutMapping("{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public Task updateCompletionStatus(@PathVariable int id, @RequestParam(defaultValue = "false") boolean completed) {
        return taskService.updateCompletionStatus(id, completed);
    }

    @GetMapping("/filter/status")
    public List<Task> filterByCompleted(@RequestParam() Boolean completed) {
        return taskService.filterByCompleted(completed);
    }

    @GetMapping("/filter/priority")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> filterByPriority(@RequestParam Priority priority) {
        return taskService.filterByPriority(priority);
    }

    @GetMapping("/filter/due-date")
    public List<Task> filterByDueDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        return taskService.filterByDueDate(dueDate);
    }
}
