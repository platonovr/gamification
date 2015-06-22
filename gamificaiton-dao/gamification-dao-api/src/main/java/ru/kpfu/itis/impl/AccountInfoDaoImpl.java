package ru.kpfu.itis.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.AccountInfoDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

/**
 * Created by Rigen on 22.06.15.
 */
@Repository
public class AccountInfoDaoImpl extends SimpleDaoImpl implements AccountInfoDao {
    @Override
    public AccountInfo findByAccount(Account account) {
        return findByField(AccountInfo.class, "account", account);
    }
}
