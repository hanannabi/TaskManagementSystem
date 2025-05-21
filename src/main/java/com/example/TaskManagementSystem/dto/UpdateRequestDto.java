package com.example.TaskManagementSystem.dto;

import java.time.LocalDate;

public class UpdateRequestDto {

    private LocalDate dueDate;
    private String title;

    public UpdateRequestDto( LocalDate dueDate, String title) {
        this.dueDate = dueDate;
        this.title = title;
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
