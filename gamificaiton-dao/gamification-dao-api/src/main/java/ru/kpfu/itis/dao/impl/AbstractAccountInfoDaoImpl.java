package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.AccountInfoDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAccountInfoDaoImpl extends AbstractGenericDao implements AccountInfoDao {
    @Override
    public AccountInfo findByAccount(Account account) {
        return getHibernateTemplate().execute(new HibernateCallback<AccountInfo>() {
            @Override
            public AccountInfo doInHibernate(Session session) throws HibernateException {
                return (AccountInfo) session.createQuery(" from AccountInfo  ai left join fetch ai.account ac " +
                        " where ac.id = :accountId")
                        .setParameter("accountId", account.getId())
                        .uniqueResult();
            }
        });
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
                        .addOrder(Order.asc("point")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
    }
}
