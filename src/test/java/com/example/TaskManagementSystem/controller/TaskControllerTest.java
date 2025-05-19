package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.advice.ExceptionControllerAdvice;
import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.exception.TaskNotFoundException;
import com.example.TaskManagementSystem.fixture.TaskFixture;
import com.example.TaskManagementSystem.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.example.TaskManagementSystem.enums.Priority.P0;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@ContextConfiguration(classes = TaskController.class)
@Import(ExceptionControllerAdvice.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTask() throws Exception {
        Task inputTask = TaskFixture.getTask();
        Task savedTask = TaskFixture.getTask();

        when(taskService.create(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.dueDate").value("2025-05-20"))
                .andExpect(jsonPath("$.priority").value("P0"))
                .andExpect(jsonPath("$.completed").value("false"));
    }

    @Test
    void shouldGetTask() throws Exception {
        ResponseDto response = new ResponseDto(1, "hello", "hello hello",
                LocalDate.of(2025, 05, 11), P0);

        when(taskService.getById(1)).thenReturn(response);

        mockMvc.perform(get("/task/getById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("hello"))
                .andExpect(jsonPath("$.description").value("hello hello"))
                .andExpect(jsonPath("$.dueDate").value("2025-05-11"));
    }

    @Test
    void findByIdShouldGiveNotFound() throws Exception {
        when(taskService.getById(any(Integer.class)))
                .thenThrow(new TaskNotFoundException(1));

        mockMvc.perform(get("/task/getById/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task with id 1 not found"));
    }
}