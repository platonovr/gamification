package ru.kpfu.itis.dao.security;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.Account;

/**
 * @date 30.04.14
 */

@Repository("securityDao")
public class SecurityDaoImpl extends AbstractDaoImpl<Account, Long>
        implements SecurityDao {

    protected SecurityDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account getAccount(Long accountId) {
        return (Account) getCurrentSession().get(Account.class, accountId);
    }

    @Override
    @Transactional
    public <T extends Account> T saveAccount(Class<T> accountClass, T account) {
        Session currentSession = getCurrentSession();
        currentSession.saveOrUpdate(account);
        return (T) findById(account.getId());
    }


}
