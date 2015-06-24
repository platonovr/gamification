package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountTask;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
public interface AccountTaskService {
    List<AccountTask> findAllTasksByAccount(Account account);

    List<AccountTask> findCompleteTasksByAccount(Account account);

    List<AccountTask> findPerformTasksByAccount(Account account);
}
