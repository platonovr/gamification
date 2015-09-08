package ru.kpfu.itis.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.enums.Role;

import java.math.BigInteger;
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
    public Task findByName(String name) {
        return getHibernateTemplate().execute(new HibernateCallback<Task>() {
            @Override
            public Task doInHibernate(Session session) throws HibernateException {
                return (Task) session.createQuery(" from Task t " +
                        " where t.name = :name")
                        .setParameter("name", name)
                        .setReadOnly(true)
                        .uniqueResult();
            }
        });
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
            String queryHql = "select task from Task task " +
                    "left join fetch task.subject " +
                    "left join task.academicGroups tAcGroupes " +
                    "left join tAcGroupes.accountInfos tacAccInf " +
                    "left join task.taskAccounts ttacc " +
                    "where current_date<=task.endDate and (:userId in tacAccInf.account.id " +
                    "or :userId in (select tacc.id from ttacc.account tacc)) ";

            if (status != null) {
                queryHql += " and ttacc.taskStatus.type=:status";
            }
            queryHql += " order by task.createTime DESC";
            Query query = session.createQuery(queryHql);
            query.setParameter("userId", userId).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (status != null) {
                query.setParameter("status", status);
            }
            if (offset != null) query.setFirstResult(offset);
            if (limit != null) query.setMaxResults(limit);
            return query.list();
        });
    }

    @Override
    public List<Task> getCreatedTasks(Account user, Integer offset, Integer limit, final String queryString) {
        boolean isAdmin = user != null && user.getRole() == Role.ADMIN;
        return getHibernateTemplate().<List<Task>>execute(session -> {
            StringBuilder queryBuilder = new StringBuilder("from Task t " +
                    "left join fetch t.academicGroups " +
                    "left join fetch t.taskAccounts att " +
                    "left join fetch att.account attc " +
                    "left join fetch attc.accountInfo " +
                    "where 1=1");
            if (isAdmin) {
                queryBuilder.append(" and t.author.id=:userId ");
            }
            if (StringUtils.isNotEmpty(queryString)) {
                queryBuilder.append(" and lower(t.name) LIKE :query");
            }
            queryBuilder.append(" order by t.createTime desc");
            Query query = session
                    .createQuery(queryBuilder.toString())
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (StringUtils.isNotEmpty(queryString)) {
                query = query.setParameter("query", "%" + queryString.toLowerCase() + "%");
            }
            if (isAdmin) {
                query = query.setParameter("userId", user.getId());
            }
            if (offset != null) query.setFirstResult(offset);
            if (limit != null) query.setMaxResults(limit);
            return query.list();
        });
    }

    @Override
    public boolean isTaskAvailableForUser(Account user, Long taskId) {
        return getHibernateTemplate().execute(session -> {
            BigInteger accountTaskCount = (BigInteger) session
                    .createSQLQuery("SELECT COUNT(*) FROM account_task atask WHERE atask.account_id = :id and atask.task_id = :taskId")
                    .setParameter("id", user.getId())
                    .setParameter("taskId", taskId).uniqueResult();
            AcademicGroup group = user.getAccountInfo().getGroup();
            BigInteger groupTaskCount = new BigInteger(String.valueOf(0l));
            if (group != null) {
                groupTaskCount = (BigInteger) session
                        .createSQLQuery("SELECT COUNT(*) FROM task_constraint tc WHERE tc.academic_group_id = :group_id and tc.task_id = :taskId")
                        .setParameter("group_id", group.getId())
                        .setParameter("taskId", taskId).uniqueResult();
            }
            return accountTaskCount.longValue() != 0 || groupTaskCount.longValue() != 0;
        });
    }
}
