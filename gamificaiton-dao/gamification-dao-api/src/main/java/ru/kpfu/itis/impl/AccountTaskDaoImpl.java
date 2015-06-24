package ru.kpfu.itis.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.AccountTaskDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountTask;
import ru.kpfu.itis.model.enums.TaskStatus;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
@Repository
public class AccountTaskDaoImpl extends SimpleDaoImpl implements AccountTaskDao {
    @Override
    public List<AccountTask> findAllTasksByAccount(Account account) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountTask>) aSession.createCriteria(AccountTask.class)
                        .add(Restrictions.eq("account", account)).list());
    }

    @Override
    public List<AccountTask> findCompleteTasksByAccount(Account account) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountTask>) aSession.createCriteria(AccountTask.class)
                        .add(Restrictions.eq("account", account))
                        .add(Restrictions.eq("taskStatus", TaskStatus.COMPLETE)).list());
    }

    @Override
    public List<AccountTask> findPerformTasksByAccount(Account account) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountTask>) aSession.createCriteria(AccountTask.class)
                        .add(Restrictions.eq("account", account))
                        .add(Restrictions.eq("taskStatus", TaskStatus.PERFORM)).list());
    }
}
