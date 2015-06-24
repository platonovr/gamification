package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.AccountTaskDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountTask;
import ru.kpfu.itis.service.AccountTaskService;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
@Service
public class AccountTaskServiceImpl implements AccountTaskService {

    @Autowired
    AccountTaskDao accountTaskDao;

    @Override
    public List<AccountTask> findAllTasksByAccount(Account account) {
        return accountTaskDao.findAllTasksByAccount(account);
    }

    @Override
    public List<AccountTask> findCompleteTasksByAccount(Account account) {
        return accountTaskDao.findCompleteTasksByAccount(account);
    }

    @Override
    public List<AccountTask> findPerformTasksByAccount(Account account) {
        return accountTaskDao.findPerformTasksByAccount(account);
    }
}
