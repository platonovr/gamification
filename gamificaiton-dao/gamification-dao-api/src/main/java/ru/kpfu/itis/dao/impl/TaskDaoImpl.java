package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
        return getHibernateTemplate().<List<Task>>execute(session -> session.createQuery("from Task task " +
                " where task.finishTime is null")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list());
    }

    @Override
    public List<Task> getTasksByUser(Long userId) {
        return getHibernateTemplate().<List<Task>>execute(session -> session.createQuery("from Task task " +
                " left join fetch task.taskAccounts ta " +
                " where ta.account.id = :userId")
                .setParameter("userId", userId)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list());
    }

    @Override
    public List<Task> getAvailableTasksByUser(Long userId, Integer offset, Integer maxResult) {
        return getHibernateTemplate().<List<Task>>execute(session -> {
            Query query = session
                    .createQuery("select distinct t from Task t " +
                            "left join t.academicGroups tAcGroupes " +
                            "left join t.taskAccounts ttacc " +
                            "where :userId in (select tacAccIng.id from tAcGroupes.accountInfos tacAccIng) " +
                            "and (ttacc is empty or :userId not in (ttacc.account.id))" +
                            "order by t.createTime desc")
                    .setParameter("userId", userId);
            if (offset != null) query.setFirstResult(offset);
            if (maxResult != null) query.setMaxResults(maxResult);
            return query.list();
        });
    }
}
