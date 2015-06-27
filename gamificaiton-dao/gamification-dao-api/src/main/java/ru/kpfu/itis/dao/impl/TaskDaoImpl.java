package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.model.Task;

import java.util.List;

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

    @Override
    public List<Task> getActualTasks() {
        return getHibernateTemplate().execute(new HibernateCallback<List<Task>>() {
            @Override
            public List<Task> doInHibernate(Session session) throws HibernateException {
                return session.createQuery("from Task task " +
                        " where task.finishTime is null")
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .list();
            }
        });
    }

    @Override
    public List<Task> getTasksByUser(Long userId) {
        return getHibernateTemplate().execute(new HibernateCallback<List<Task>>() {
            @Override
            public List<Task> doInHibernate(Session session) throws HibernateException {
                return session.createQuery("from Task task " +
                        " left join fetch task.taskAccounts ta " +
                        " where ta.account.id = :userId")
                        .setParameter("userId", userId)
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .list();
            }
        });
    }
}
