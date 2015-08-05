package ru.kpfu.itis.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountTaskDao;
import ru.kpfu.itis.model.AccountTask;

/**
 * Created by Rigen on 02.07.15.
 */
@Repository
public class AccountTaskDaoImpl extends SimpleDaoImpl implements AccountTaskDao {

    @Override
    public AccountTask findByTaskAndAccount(Long taskId, Long accountId) {
        return getHibernateTemplate().execute((aSession) ->
                (AccountTask) aSession.createCriteria(AccountTask.class)
                        .add(Restrictions.eq("account.id", accountId))
                        .add(Restrictions.eq("task.id", taskId))
                        .add(Restrictions.isNull("finishTime")).uniqueResult());
    }

    @Override
    public AccountTask findByTaskIdAndAccountId(Long taskId, Long accountId) {
        return getHibernateTemplate().execute(session ->
                (AccountTask) session.createQuery("select at from AccountTask at " +
                        "left join at.task att left join at.account atc " +
                        "left join fetch at.taskStatus " +
                        "where att.id=:taskId and atc.id=:accountId")
                        .setParameter("taskId", taskId)
                        .setParameter("accountId", accountId)
                        .uniqueResult());
    }
}
