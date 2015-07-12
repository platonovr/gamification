package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.TaskCategoryDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;
import ru.kpfu.itis.model.TaskStatus;

import java.util.List;

/**
 * Created by timur on 17.06.15.
 */
public interface TaskService {

    Task submitTask(Task task);

    Task save(TaskDto taskDto);

    Task findByName(String name);

    Task findById(Long id);

    List<TaskCategoryDto> getAllCategories();

    TaskCategory save(TaskCategory taskCategory);

    List<Task> getActualTasks();

    List<Task> getTasksByUser(Long userId);

    TaskDto findById(Long taskId);

    /**
     * Get available tasks (if status null) or tasks with specified status
     *
     * @param userId student id
     * @return if status null, will return available tasks, else - tasks with specified status
     */
    List<TaskDto> getTasksByUser(Long userId, Integer offset, Integer limit,
                                 TaskStatus.TaskStatusType status);

    /**
     * @param userId admin or teacher id
     */
    List<TaskDto> getCreatedTasks(Long userId, Integer offset, Integer limit);
}
