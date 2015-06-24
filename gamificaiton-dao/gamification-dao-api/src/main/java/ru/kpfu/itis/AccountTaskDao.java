package ru.kpfu.itis;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountTask;
import ru.kpfu.itis.model.Task;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
public interface AccountTaskDao extends SimpleDao {
    List<AccountTask> findAllTasksByAccount(Account account);

    List<AccountTask> findCompleteTasksByAccount(Account account);

    List<AccountTask> findPerformTasksByAccount(Account account);
}
