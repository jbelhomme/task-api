package com.task.api.mapper;

import com.task.api.dto.TaskDto;
import com.task.api.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskMapperTests {

    @Autowired
    public TaskMapper taskMapper;

    @Test
    public void toDto_test() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(777L);
        taskDto.setLabel("Task One");
        taskDto.setComplete(false);

        Task task = taskMapper.toTask(taskDto);
        Assertions.assertEquals(taskDto.getId(), task.getId());
        Assertions.assertEquals(taskDto.getLabel(), task.getLabel());
        Assertions.assertEquals(taskDto.isComplete(), task.isComplete());
    }

    @Test
    public void toTask_test() {
        Task task = new Task();
        task.setId(222L);
        task.setLabel("Task Two");
        task.setComplete(true);

        TaskDto taskDto = taskMapper.toDto(task);
        Assertions.assertEquals(task.getId(), taskDto.getId());
        Assertions.assertEquals(task.getLabel(), taskDto.getLabel());
        Assertions.assertEquals(task.isComplete(), taskDto.isComplete());
    }
}
