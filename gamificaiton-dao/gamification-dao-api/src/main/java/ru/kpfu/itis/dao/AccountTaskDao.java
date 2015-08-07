package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.AccountTask;

/**
 * Created by Rigen on 02.07.15.
 */
public interface AccountTaskDao extends AbstractDao<AccountTask, Long> {
    AccountTask findByTaskAndAccount(Long taskId, Long accountId);

    AccountTask findByTaskIdAndAccountId(Long taskId, Long accountId);

}
