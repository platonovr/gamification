package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.AccountTask;

/**
 * Created by Rigen on 02.07.15.
 */
public interface AccountTaskDao extends SimpleDao {
    AccountTask findByTaskAndAccount(Long taskId, Long accountId);
}
