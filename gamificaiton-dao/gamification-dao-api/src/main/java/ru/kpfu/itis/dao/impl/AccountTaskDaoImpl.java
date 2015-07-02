package ru.kpfu.itis.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountTaskDao;
import ru.kpfu.itis.model.AccountTask;

/**
 * Created by Rigen on 02.07.15.
 */
@Repository
public class AccountTaskDaoImpl extends SimpleDaoImpl implements AccountTaskDao {

    @Transactional
    @Override
    public AccountTask findByTaskAndAccount(Long taskId, Long accountId) {
        return getHibernateTemplate().execute((aSession) ->
                (AccountTask) aSession.createCriteria(AccountTask.class)
                        .add(Restrictions.eq("account.id", accountId))
                        .add(Restrictions.eq("task.id", taskId))
                        .add(Restrictions.isNull("finishTime")).uniqueResult());
    }
}
