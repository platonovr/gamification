package ru.kpfu.itis.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountBadgeDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@Repository
public class AccountBadgeDaoImpl extends SimpleDaoImpl implements AccountBadgeDao {
    @Override
    public List<AccountBadge> findAllBadgesByAccount(Account account) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountBadge>) aSession.createCriteria(AccountBadge.class)
                        .add(Restrictions.eq("account.id", account.getId()))
                        .add(Restrictions.isNull("finishTime")).list());
    }
}
