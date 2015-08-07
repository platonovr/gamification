package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Account;

/**
 * Created by timur on 24.06.15.
 */
public interface AccountDao extends AbstractDao<Account, Long> {

    Account findByLogin(String login);
}
