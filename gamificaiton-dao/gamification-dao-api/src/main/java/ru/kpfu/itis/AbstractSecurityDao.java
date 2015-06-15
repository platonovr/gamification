package ru.kpfu.itis;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;

import java.util.UUID;

/**
 * @date 30.04.14
 */
public abstract class AbstractSecurityDao extends AbstractGenericDao
        implements SecurityDao {

    @Override
    public Account getAccount(UUID accountId) {
        return getHibernateTemplate().get(Account.class, accountId);
    }


    @Override
    @Transactional
    public <T extends Account> T saveAccount(Class<T> accountClass, T account) {
        getHibernateTemplate().saveOrUpdate(account);
        return account;
    }

}
