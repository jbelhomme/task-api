package com.task.api.dataset;

import com.task.api.model.Task;

public class TaskDataset {
    public static Task createTask(Long id, String label, boolean complete) {
        Task task = new Task();
        task.setId(id);
        task.setLabel(label);
        task.setComplete(complete);
        return task;
    }
}
