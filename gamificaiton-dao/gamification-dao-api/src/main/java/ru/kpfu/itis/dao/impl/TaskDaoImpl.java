package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskStatus;

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
    public List<Task> getTasksByUser(Long userId, Integer offset, Integer limit, TaskStatus.TaskStatusType status) {
        return getHibernateTemplate().<List<Task>>execute(session -> {
            Query query;
            if (status == null)
                query = session.createQuery("select t from Task t " +
                        "left join t.academicGroups tAcGroupes " +
                        "left join t.taskAccounts ttacc " +
                        "where :userId in (select tacAccIng.account.id from tAcGroupes.accountInfos tacAccIng) " +
                        "and (ttacc is empty or :userId not in (select tacc.id from ttacc.account tacc))" +
                        "order by t.endDate");
            else query = session.createQuery("from Task task " +
                    "left join fetch task.taskAccounts ta join fetch ta.account taa left join fetch taa.accountInfo " +
                    "where ta.account.id = :userId and ta.taskStatus.type=:status")
                    .setParameter("status", status);
            query.setParameter("userId", userId).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (offset != null) query.setFirstResult(offset);
            if (limit != null) query.setMaxResults(limit);
            return query.list();
        });
    }

    @Override
    public List<Task> getCreatedTasks(Long userId, Integer offset, Integer limit) {
        return getHibernateTemplate().<List<Task>>execute(session -> {
            Query query = session
                    .createQuery("from Task t " +
                            "left join fetch t.taskAccounts att " +
                            "left join fetch att.account attc " +
                            "left join fetch attc.accountInfo " +
                            "where t.author.id=:userId " +
                            "order by t.createTime desc")
                    .setParameter("userId", userId)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (offset != null) query.setFirstResult(offset);
            if (limit != null) query.setMaxResults(limit);
            return query.list();
        });
    }
}
