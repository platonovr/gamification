package ru.kpfu.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.Account;

/**
 * Created by timur on 24.06.15.
 */
@Repository("accountDao")
public class AccountDaoImpl extends AbstractDaoImpl<Account, Long> implements AccountDao {

    protected AccountDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account findByLogin(String login) {
        return findByField("login", login);
    }
}
