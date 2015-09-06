package ru.kpfu.itis.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.AcademicGroup;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

            queryHql += " order by task.startDate DESC";
            Query query = session.createQuery(queryHql);
            query.setParameter("userId", userId).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (offset != null) query.setFirstResult(offset);
            if (limit != null) query.setMaxResults(limit);
            List<Task> resultList = new ArrayList<Task>();
            List<Task> allTasks = new ArrayList<Task>(query.list());
            for (Task task : allTasks) {
                if (task.getTaskAccounts().isEmpty()) {
                    resultList.add(task);
                } else {
                    resultList.addAll(task.getTaskAccounts().stream().filter(accountTask -> TaskStatus.TaskStatusType.ASSIGNED.equals(accountTask.getTaskStatus().getType())).map(accountTask -> task).collect(Collectors.toList()));
                }
            }
            return resultList;
        });
    }

    @Override
    public List<Task> getCreatedTasks(Long userId, Integer offset, Integer limit, final String queryString) {
        return getHibernateTemplate().<List<Task>>execute(session -> {
            StringBuilder queryBuilder = new StringBuilder("from Task t " +
                    "left join fetch t.academicGroups " +
                    "left join fetch t.taskAccounts att " +
                    "left join fetch att.account attc " +
                    "left join fetch attc.accountInfo " +
                    "where t.author.id=:userId");
            if (StringUtils.isNotEmpty(queryString)) {
                queryBuilder.append(" and lower(t.name) LIKE :query");
            }
            queryBuilder.append(" order by t.createTime desc");
            Query query = session
                    .createQuery(queryBuilder.toString())
                    .setParameter("userId", userId)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (StringUtils.isNotEmpty(queryString)) {
                query = query.setParameter("query", "%" + queryString.toLowerCase() + "%");
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
