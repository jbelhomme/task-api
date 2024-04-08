package com.task.api.mapper;

import com.task.api.dto.TaskDto;
import com.task.api.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDto toDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setLabel(task.getLabel());
        taskDto.setComplete(task.isComplete());

        return taskDto;
    }

    public Task toTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setLabel(taskDto.getLabel());
        task.setComplete(taskDto.isComplete());
        return task;
    }
}
