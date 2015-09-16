package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.enums.Role;

import java.util.List;

/**
 * Created by timur on 24.06.15.
 */

@SuppressWarnings("unchecked")
public abstract class AbstractAccountDaoImpl extends AbstractGenericDao implements AccountDao {

    @Override
    public Account findByLogin(String login) {
        return getHibernateTemplate().execute(session -> (Account) session.createQuery("from Account a" +
                " where a.login = :login")
                .setParameter("login", login)
                .uniqueResult());
    }

    @Override
    public List<Account> getAccountsByRole(Role type) {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<Account>>() {
                    @Override
                    public List<Account> doInHibernate(Session session) throws HibernateException {
                        return session.createQuery(
                                " from Account acc " +
                                        " join  fetch acc.accountInfo ai " +
                                        " left join  fetch ai.group g " +
                                        " left join  fetch g.course " +
                                        " where  acc.role = :neededType"
                        ).setParameter("neededType", type)
                                .setReadOnly(true)
                                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                                .list();
                    }
                }
        );
    }
}
