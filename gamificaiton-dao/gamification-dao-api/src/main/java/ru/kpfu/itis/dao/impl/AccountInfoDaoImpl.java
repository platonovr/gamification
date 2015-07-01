package ru.kpfu.itis.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountInfoDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@Repository
public class AccountInfoDaoImpl extends SimpleDaoImpl implements AccountInfoDao {
    @Override
    public AccountInfo findByAccount(Account account) {
        return findByField(AccountInfo.class, "account", account);
    }

    @Override
    public AccountInfo findByAccountId(Long id) {
        return getHibernateTemplate().execute((aSession) ->
                (AccountInfo) aSession.createCriteria(AccountInfo.class)
                        .add(Restrictions.eq("account.id", id))
                        .add(Restrictions.isNull("finishTime")).uniqueResult());
    }

    @Override
    public List<AccountInfo> getAllAndSort(AccountInfo accountInfo) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountInfo>) aSession.createCriteria(AccountInfo.class)
                        .add(Restrictions.eq("entranceYear", accountInfo.getEntranceYear()))
                        .add(Restrictions.isNull("finishTime"))
                        .addOrder(Order.asc("point")).list());
    }
}
