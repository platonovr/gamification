package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountInfoDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@Repository
public class AccountInfoDaoImpl extends AbstractDaoImpl<AccountInfo, Long> implements AccountInfoDao {
    protected AccountInfoDaoImpl() {
        super(AccountInfo.class);
    }

    @Override
    public AccountInfo findByAccount(Account account) {
        return findByField("account", account);
    }

    @Override
    public AccountInfo findByAccountId(Long id) {
        return (AccountInfo) getCurrentSession().createCriteria(AccountInfo.class)
                .add(Restrictions.eq("account.id", id))
                .add(Restrictions.isNull("finishTime")).uniqueResult();
    }

    @Override
    public List<AccountInfo> getAllAndSort(AccountInfo accountInfo) {
        return getCurrentSession().createCriteria(AccountInfo.class)
                .add(Restrictions.eq("entranceYear", accountInfo.getEntranceYear()))
                .add(Restrictions.isNull("finishTime"))
                .addOrder(Order.asc("point")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
}
