package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Task;

import java.util.List;

/**
 * Created by timur on 23.06.15.
 */
public interface TaskDao extends SimpleDao {

    Task submitTask(Task task);

    List<Task> getActualTasks();

    List<Task> getTasksByUser(Long userId);

    List<Task> getAvailableTasksByUser(Long userId, Integer offset, Integer maxResult);
}
