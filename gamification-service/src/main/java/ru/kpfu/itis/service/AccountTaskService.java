package ru.kpfu.itis.service;

import ru.kpfu.itis.model.AccountTask;

/**
 * Created by Rigen on 02.07.15.
 */
public interface AccountTaskService {
    AccountTask findByTaskAndAccount(Long taskId, Long accountId);

    void saveOrUpdate(AccountTask accountTask);
}
