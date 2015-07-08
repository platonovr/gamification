package ru.kpfu.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dao.AccountTaskDao;
import ru.kpfu.itis.model.AccountTask;

/**
 * Created by Rigen on 02.07.15.
 */
@Service
public class AccountTaskServiceImpl implements AccountTaskService {

    @Autowired
    AccountTaskDao accountTaskDao;

    @Override
    public AccountTask findByTaskAndAccount(Long taskId, Long accountId) {
        return accountTaskDao.findByTaskAndAccount(taskId, accountId);
    }

    @Override
    public void saveOrUpdate(AccountTask accountTask) {
        accountTaskDao.saveOrUpdate(accountTask);
    }
}
