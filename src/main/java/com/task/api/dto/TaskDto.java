package com.task.api.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Long id;

    private String label;

    private boolean complete;
}
