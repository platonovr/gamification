package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.enums.Role;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.jbl.auth.domain.AuthUser;
import ru.kpfu.jbl.auth.response.UserResponse;

import java.io.Serializable;

import static java.util.Optional.ofNullable;

/**
 * Created by Rigen on 22.06.15.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SimpleDao simpleDao;

    @Transactional
    @Override
    public Account findById(Long id) {
        return simpleDao.findById(Account.class, id);
    }

    @Override
    public AuthUser findUserByLogin(String s) {
        return accountDao.findByLogin(s);
    }

    @Override
    public AuthUser saveUser(UserResponse userResponse) {
        if (userResponse.getId() == null) {
            return null;
        }
        Account account = ofNullable(simpleDao.findById(Account.class, userResponse.getId())).orElse(accountDao.findByLogin(userResponse.getLogin()));
        if (account == null) {
            //TODO proper save user
            account = new Account();
            account.setLogin(userResponse.getLogin());
            account.setRole(Role.valueOf(userResponse.getRole()));
            account.setPassword("");
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setFirstName(userResponse.getName());
            Serializable id = simpleDao.save(account);
            account = simpleDao.findById(Account.class, id);
        }
        return account;
    }
}
