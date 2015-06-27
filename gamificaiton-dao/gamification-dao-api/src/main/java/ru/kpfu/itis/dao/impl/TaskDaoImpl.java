package ru.kpfu.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.model.Task;

/**
 * Created by timur on 23.06.15.
 */
@Repository("taskDao")
public class TaskDaoImpl extends SimpleDaoImpl implements TaskDao {
    @Override
    public Task submitTask(Task task) {
        getHibernateTemplate().saveOrUpdate(task);
        return task;
    }
}
