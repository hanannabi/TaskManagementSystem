package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.advice.ExceptionControllerAdvice;
import com.example.TaskManagementSystem.dto.ResponseDto;
import com.example.TaskManagementSystem.dto.UpdateRequestDto;
import com.example.TaskManagementSystem.dto.UpdateResponseDto;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.enums.Priority;
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
import java.util.List;

import static com.example.TaskManagementSystem.enums.Priority.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void shouldUpdateTask() throws Exception {
        UpdateRequestDto updateRequestDto = new UpdateRequestDto(LocalDate.of(2025, 05, 11), "whatever");
        UpdateResponseDto updateResponseDto = new UpdateResponseDto(1, "hello dto", LocalDate.of(2025, 05, 11));

        when(taskService.update(any(UpdateRequestDto.class), eq(1))).thenReturn(updateResponseDto);

        mockMvc.perform(put("/task/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDto)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("hello dto"))
                .andExpect(jsonPath("$.dueDate").value("2025-05-11"));
    }


    @Test
    void shouldDeleteTask() throws Exception {
        int id = 1;
        String response = "Task deleted successfully";

        when(taskService.delete(id)).thenReturn(response);

        mockMvc.perform(delete("/task/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }


    @Test
    void shouldUpdateCompletionOfTask() throws Exception {
        int id = 1;
        boolean completed = true;
        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTitle("hi");
        updatedTask.setDescription("helllo");
        updatedTask.setPriority(P0);
        updatedTask.setDueDate(LocalDate.of(2025,05,11));
        updatedTask.setCompleted(true);

        when(taskService.updateCompletionStatus(id,completed)).thenReturn(updatedTask);

        mockMvc.perform(put("/task/{id}/status",1)
                .param("completed",String.valueOf(completed)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("hi"))
                .andExpect(jsonPath("$.completed").value(true));
    }


    @Test
    void shouldGetFilterByPriorityListOfTasK() throws Exception {

        Priority priority = P0;
        List<Task> taskList= List.of(new Task(1,"hello","hiii",LocalDate.of(2025,05,11),P4,true),
                new Task(2,"hellog","hihi",LocalDate.of(2025,06,12),P2,true));


        when(taskService.filterByPriority(priority)).thenReturn(taskList);

        mockMvc.perform(get("/task/filter/priority")
                .param("priority",priority.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].priority").value(P4.name()))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].priority").value(P2.name()));


    }

}