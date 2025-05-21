package com.example.TaskManagementSystem.dto;

import java.time.LocalDate;

public class UpdateResponseDto {
    private int id;
    private LocalDate dueDate;
    private String title;

    public UpdateResponseDto(int id,String title, LocalDate dueDate) {
        this.id=id;
        this.title=title;
        this.dueDate=dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
