package com.task.api.dataset;

import com.task.api.model.Task;

public class TaskDataset {
    public static Task createTask(String label, boolean complete) {
        Task task = new Task();
        task.setLabel(label);
        task.setComplete(complete);
        return task;
    }
}
