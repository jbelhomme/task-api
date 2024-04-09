package com.task.api.service;

import com.task.api.dataset.TaskDataset;
import com.task.api.dto.TaskDto;
import com.task.api.mapper.TaskMapper;
import com.task.api.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class TaskServiceTests {

    @Autowired
    @InjectMocks
    public TaskService taskService;

    @Autowired
    public TaskMapper taskMapper;

    @Test
    @Order(1)
    public void saveTask_test() {
        String label = "Task TEST";
        boolean complete = true;
        Task newTask = TaskDataset.createTask(label, complete);

        TaskDto resultTask = taskService.saveTask(taskMapper.toDto(newTask));

        Assertions.assertEquals(1L, resultTask.getId());
        Assertions.assertEquals(label, resultTask.getLabel());
        Assertions.assertEquals(complete, resultTask.isComplete());
        taskService.deleteTaskById(resultTask.getId());
    }

    @Test
    @Order(2)
    public void updateTask_test() {
        Task newTask = TaskDataset.createTask("Task update", false);
        TaskDto newTaskDto = taskService.saveTask(taskMapper.toDto(newTask));

        String label = "Task updated";
        TaskDto taskDtoForUpdate = new TaskDto();
        taskDtoForUpdate.setLabel(label);
        taskDtoForUpdate.setComplete(true);

        TaskDto resultTask = taskService.updateTask(taskDtoForUpdate, newTaskDto.getId());

        Assertions.assertEquals(newTaskDto.getId(), resultTask.getId());
        Assertions.assertEquals(label, resultTask.getLabel());
        Assertions.assertTrue(resultTask.isComplete());
        taskService.deleteTaskById(newTaskDto.getId());
    }

    @Test
    @Order(3)
    public void getTask_test() {
        String label = "Task AB";
        Task newTask = TaskDataset.createTask(label, false);
        TaskDto newTaskDto = taskService.saveTask(taskMapper.toDto(newTask));
        TaskDto taskDto = taskService.getTask(newTaskDto.getId());
        Assertions.assertEquals(label, taskDto.getLabel());
        taskService.deleteTaskById(newTaskDto.getId());
    }

    @Test
    @Order(4)
    public void getTasks_test() {
        TaskDto taskDto1 = taskService.saveTask(taskMapper.toDto(TaskDataset.createTask("Task 1", false)));
        TaskDto taskDto2 = taskService.saveTask(taskMapper.toDto(TaskDataset.createTask("Task 2", false)));
        TaskDto taskDto3 = taskService.saveTask(taskMapper.toDto(TaskDataset.createTask("Task 3", false)));
        TaskDto taskDto4 = taskService.saveTask(taskMapper.toDto(TaskDataset.createTask("Task 4", false)));
        TaskDto taskDto5 = taskService.saveTask(taskMapper.toDto(TaskDataset.createTask("Task 5", true)));
        TaskDto taskDto6 = taskService.saveTask(taskMapper.toDto(TaskDataset.createTask("Task 6", false)));

        Pageable pageable = Pageable.ofSize(5);
        Page<TaskDto> tasks = taskService.getAllTasks(pageable, false);
        Assertions.assertEquals(6, tasks.getTotalElements());
        Assertions.assertEquals(5, tasks.getNumberOfElements());
        Assertions.assertEquals("Task 5", tasks.getContent().get(4).getLabel());
        Page<TaskDto> tasksWithoutComplete = taskService.getAllTasks(pageable, true);
        Assertions.assertEquals(5, tasksWithoutComplete.getTotalElements());
        Assertions.assertEquals("Task 6", tasksWithoutComplete.getContent().get(4).getLabel());

        taskService.deleteTaskById(taskDto1.getId());
        taskService.deleteTaskById(taskDto2.getId());
        taskService.deleteTaskById(taskDto3.getId());
        taskService.deleteTaskById(taskDto4.getId());
        taskService.deleteTaskById(taskDto5.getId());
        taskService.deleteTaskById(taskDto6.getId());
    }
}
