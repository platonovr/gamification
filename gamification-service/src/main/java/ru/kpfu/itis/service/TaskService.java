package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;

import java.util.Collection;
import java.util.List;

/**
 * Created by timur on 17.06.15.
 */
public interface TaskService {

    Task submitTask(Task task);

    Task save(TaskDto taskDto);

    Task findByName(String name);

    Collection<TaskCategory> getAllCategories();

    TaskCategory save(TaskCategory taskCategory);

    List<Task> getActualTasks();

    List<Task> getTasksByUser(Long userId);
}
