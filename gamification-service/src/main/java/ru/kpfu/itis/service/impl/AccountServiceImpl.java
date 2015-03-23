package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.repository.AccountRepository;
import ru.kpfu.itis.service.AccountService;

/**
 * Created by Дамир on 06.02.2015.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findUserByLogin(String login) {
        return accountRepository.findUserByLogin(login);
    }
}
