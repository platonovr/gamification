package ru.kpfu.itis.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.AccountInfoDao;
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
    public AccountInfo findById(Long id) {
        return findByField(AccountInfo.class, "account.id", id);
    }

    @Override
    public List<AccountInfo> getAllAndSort(AccountInfo accountInfo) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountInfo>) aSession.createCriteria(AccountInfo.class)
                        .add(Restrictions.eq("entranceYear", accountInfo.getEntranceYear()))
                        .addOrder(Order.asc("point")).list());
    }
}
