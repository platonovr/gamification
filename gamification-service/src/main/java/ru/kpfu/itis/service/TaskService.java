package ru.kpfu.itis.service;

import org.springframework.security.access.annotation.Secured;
import ru.kpfu.itis.dto.TaskCategoryDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.dto.TaskInfoDto;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskStatus;
import ru.kpfu.itis.model.classifier.TaskCategory;

import java.util.List;

/**
 * Created by timur on 17.06.15.
 */
public interface TaskService {

    Task submitTask(Task task);

    @Secured({"ADMIN", "TEACHER"})
    Task save(TaskDto taskDto);

    Task findByName(String name);

    Task findTaskById(Long id);

    List<TaskCategoryDto> getAllCategories();

    TaskCategory save(TaskCategory taskCategory);

    List<Task> getActualTasks();

    List<Task> getTasksByUser(Long userId);

    TaskInfoDto findById(Long taskId);

    /**
     * Get available tasks (if status null) or tasks with specified status
     *
     * @param userId student id
     * @return if status null, will return available tasks, else - tasks with specified status
     */
    List<TaskDto> getTasksByUser(Long userId, Integer offset, Integer limit,
                                 TaskStatus.TaskStatusType status);

    /**
     * (non-Javadoc)
     *
     * @implNote implement me, example of task method
     */
    void stub();
    /**
     * @param userId admin or teacher id
     */
    List<TaskInfoDto> getCreatedTasks(Long userId, Integer offset, Integer limit);
}
