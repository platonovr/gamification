package ru.kpfu.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.model.Account;

/**
 * Created by timur on 24.06.15.
 */
@Repository("accountDao")
public class AccountDaoImpl extends SimpleDaoImpl implements AccountDao {
    @Override
    public Account findByLogin(String login) {
        return findByField(Account.class, "login", login);
    }
}
