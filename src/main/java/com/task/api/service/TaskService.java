package com.task.api.service;

import com.task.api.dto.TaskDto;
import com.task.api.mapper.TaskMapper;
import com.task.api.model.Task;
import com.task.api.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Data
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private TaskMapper taskMapper;

    /**
     * Create task
     *
     * @param task dto task attributes
     * @return task created
     */
    public TaskDto saveTask(TaskDto task) {
        Task newTask = new Task();
        newTask.setLabel(task.getLabel());
        newTask.setComplete(task.isComplete());
        taskRepo.save(newTask);
        return taskMapper.toDto(newTask);
    }

    /**
     * Update task
     *
     * @param task   dto task attributes
     * @param taskId task id to update
     * @return task created
     */
    public TaskDto updateTask(TaskDto task, Long taskId) {
        Task oldTask = taskRepo.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with id [%d] not found.", taskId))
        );
        oldTask.setLabel(task.getLabel());
        oldTask.setComplete(task.isComplete());
        taskRepo.save(oldTask);
        return taskMapper.toDto(oldTask);
    }

    /**
     * Get task by is ID
     *
     * @param taskID task id
     * @return the task or throw not found exception
     */
    public TaskDto getTask(Long taskID) {
        Task task = taskRepo.findById(taskID).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with id [%d] not found.", taskID))
        );
        return taskMapper.toDto(task);
    }

    /**
     * Get all tasks
     *
     * @param pageable   pagination
     * @param toComplete criteria for field complete
     * @return all tasks by page
     */
    public Page<TaskDto> getAllTasks(Pageable pageable, boolean toComplete) {
        Specification<Task> criteria = null;
        if (toComplete) {
            criteria = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("complete"), false));
        }
        Page<Task> allTasks = taskRepo.findAll(criteria, pageable);
        return allTasks.map(taskMapper::toDto);
    }
}
