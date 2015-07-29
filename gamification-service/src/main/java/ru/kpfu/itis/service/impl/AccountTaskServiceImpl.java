package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountTaskDao;
import ru.kpfu.itis.model.AccountTask;
import ru.kpfu.itis.service.AccountTaskService;

/**
 * Created by Rigen on 02.07.15.
 */
@Service
public class AccountTaskServiceImpl implements AccountTaskService {

    @Autowired
    AccountTaskDao accountTaskDao;

    @Override
    @Transactional
    public AccountTask findByTaskAndAccount(Long taskId, Long accountId) {
        return accountTaskDao.findByTaskAndAccount(taskId, accountId);
    }

    @Override
    @Transactional
    public void saveOrUpdate(AccountTask accountTask) {
        accountTaskDao.saveOrUpdate(accountTask);
    }
}
