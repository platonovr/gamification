package ru.kpfu.itis.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;

/**
 * Created by timur on 24.06.15.
 */

@SuppressWarnings("unchecked")
public abstract class AbstractAccountDaoImpl extends AbstractGenericDao implements AccountDao {

    @Override
    public Account findByLogin(String login) {
        return getHibernateTemplate().execute(new HibernateCallback<Account>() {
            @Override
            public Account doInHibernate(Session session) throws HibernateException {
                return (Account) session.createQuery("from Account a" +
                        " where a.login = :login")
                        .setParameter("login", login)
                        .uniqueResult();
            }
        });
    }
}
