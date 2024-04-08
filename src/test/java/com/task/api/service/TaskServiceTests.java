package com.task.api.service;

import com.task.api.dataset.TaskDataset;
import com.task.api.dto.TaskDto;
import com.task.api.mapper.TaskMapper;
import com.task.api.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskServiceTests {

    @Autowired
    @InjectMocks
    public TaskService taskService;

    @Autowired
    public TaskMapper taskMapper;

    @Test
    public void saveTask_test() {
        Long id = 227L;
        String label = "Task TEST";
        boolean complete = true;
        Task newTask = TaskDataset.createTask(id, label, complete);

        TaskDto resultTask = taskService.saveTask(taskMapper.toDto(newTask));

        Assertions.assertEquals(id, resultTask.getId());
        Assertions.assertEquals(label, resultTask.getLabel());
        Assertions.assertEquals(complete, resultTask.isComplete());
    }

    @Test
    public void updateTask_test() {
        Long id = 57L;
        Task newTask = TaskDataset.createTask(id, "Task update", false);
        taskService.saveTask(taskMapper.toDto(newTask));

        String label = "Task updated";
        TaskDto taskDtoForUpdate = new TaskDto();
        taskDtoForUpdate.setLabel(label);
        taskDtoForUpdate.setComplete(true);

        TaskDto resultTask = taskService.updateTask(taskDtoForUpdate, id);

        Assertions.assertEquals(id, resultTask.getId());
        Assertions.assertEquals(label, resultTask.getLabel());
        Assertions.assertTrue(resultTask.isComplete());
    }

    @Test
    public void getTask_test() {
        Long id = 952L;
        Task newTask = TaskDataset.createTask(id, "Task update", false);
        taskService.saveTask(taskMapper.toDto(newTask));
        TaskDto taskDto = taskService.getTask(id);
        Assertions.assertEquals(id, taskDto.getId());
    }
}
