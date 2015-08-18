package ru.kpfu.itis.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.model.Account;
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
     * @param query
     */
    List<TaskInfoDto> getCreatedTasks(Long userId, Integer offset, Integer limit, String query);

    /**
     * User takes task to do
     *
     * @param account current account
     * @param taskId  id of needed task
     * @return http code
     */
    ResponseEntity enroll(Account account, Long taskId);

    /**
     * @param accountId
     * @param taskId
     * @return resposne entity
     */

    ResponseEntity checkTask(Long taskId, Long accountId, Integer mark);


    List<BadgeDto> getAllBadges();

    BadgeDto findBadgeById(Long id);

    ErrorDto isTaskAvailableForUser(Long taskId);
}
