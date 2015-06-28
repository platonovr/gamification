package ru.kpfu.itis.dao.security;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;

/**
 * @date 30.04.14
 */

@Repository("securityDao")
public class SecurityDaoImpl extends AbstractGenericDao
        implements SecurityDao {

    @Override
    public Account getAccount(Long accountId) {
        return getHibernateTemplate().get(Account.class, accountId);
    }

    @Override
    @Transactional
    public <T extends Account> T saveAccount(Class<T> accountClass, T account) {
        getHibernateTemplate().saveOrUpdate(account);
        return account;
    }


}
