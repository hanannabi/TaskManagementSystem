package com.example.TaskManagementSystem.dto;

import java.time.LocalDate;

public class UpdateResponseDto {
    private LocalDate dueDate;
    private String title;

    public UpdateResponseDto(String title, LocalDate dueDate) {
        this.title=title;
        this.dueDate=dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
