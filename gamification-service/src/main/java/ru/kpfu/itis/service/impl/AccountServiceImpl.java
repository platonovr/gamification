package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.jbl.auth.domain.AuthUser;

/**
 * Created by Rigen on 22.06.15.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Transactional
    @Override
    public Account findById(Long id) {
        return accountDao.findById(Account.class, id);
    }

    @Override
    public AuthUser findUserByLogin(String s) {
        return accountDao.findByLogin(s);
    }
}
