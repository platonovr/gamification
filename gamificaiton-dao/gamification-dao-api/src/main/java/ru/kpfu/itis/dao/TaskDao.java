package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Task;

/**
 * Created by timur on 23.06.15.
 */
public interface TaskDao extends SimpleDao {

    Task submitTask(Task task);
}
