package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskStatus;

import java.util.List;

/**
 * Created by timur on 23.06.15.
 */
public abstract class AbstractTaskDaoImpl extends AbstractGenericDao implements TaskDao {

    @Override
    public Task submitTask(Task task) {
        getHibernateTemplate().saveOrUpdate(task);
        return task;
    }

    @Override
    public Task findById(Long id) {
        return getHibernateTemplate().execute(session -> (Task) session.createQuery("from Task t " +
                "left join fetch t.taskAccounts tacc " +
                "left join fetch t.academicGroups ag " +
                "left join fetch tacc.account taccAcc " +
                "left join fetch taccAcc.accountInfo " +
                "left join fetch tacc.taskStatus " +
                "left join fetch t.subject " +
                "left join fetch t.badge tb " +
                "left join fetch tb.subject " +
                "where t.id=:id")
                .setParameter("id", id)
                .uniqueResult());
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
            if (status == null) {
                query = session.createQuery("select task from Task task " +
                        "left join fetch task.subject " +
                        "left join task.academicGroups tAcGroupes " +
                        "left join tAcGroupes.accountInfos tacAccInf " +
                        "left join task.taskAccounts ttacc " +
                        "where current_date<=task.endDate and :userId in (tacAccInf.account.id) " +
                        "and (ttacc is empty or :userId not in (select tacc.id from ttacc.account tacc))" +
                        "order by task.endDate");
            } else {
                query = session.createQuery("select task from Task task " +
                        "left join fetch task.subject " +
                        "left join task.taskAccounts ta " +
                        "left join ta.account taa " +
                        "left join taa.accountInfo " +
                        "where current_date<=task.endDate and ta.account.id = :userId and ta.taskStatus.type=:status ")
                        .setParameter("status", status);
            }
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
                            "left join fetch t.academicGroups " +
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
