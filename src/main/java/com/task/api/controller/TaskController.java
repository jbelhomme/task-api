package com.task.api.controller;

import com.task.api.dto.TaskDto;
import com.task.api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task", description = "API for task object")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
            summary = "Create a task",
            description = "Create a new task in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "creation of the task is successful")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid TaskDto task) {
        return taskService.saveTask(task);
    }

    @Operation(
            summary = "Update a task",
            description = "Update a task by is Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update of the task is successful")
    })
    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@RequestBody @Valid TaskDto task, @PathVariable Long taskId) {
        return taskService.updateTask(task, taskId);
    }

    @Operation(
            summary = "Get a task",
            description = "Get a task by is ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "task found"),
            @ApiResponse(responseCode = "404", description = "task not found")
    })
    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @Operation(
            summary = "Get all tasks",
            description = "Get all tasks with a pagination and optional filter on field complete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tasks found")
    })
    @GetMapping(params = {"page", "size", "toComplete"})
    public Page<TaskDto> getTasks(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("toComplete") boolean toComplete) {
        Pageable pageable = PageRequest.of(page, size);
        return taskService.getAllTasks(pageable, toComplete);
    }
}
