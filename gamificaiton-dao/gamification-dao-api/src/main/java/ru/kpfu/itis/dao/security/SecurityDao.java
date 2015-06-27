package ru.kpfu.itis.dao.security;


import ru.kpfu.itis.model.Account;

/**
 * @date 30.04.14
 */
public interface SecurityDao {

    Account getAccount(Long accountId);

    <T extends Account> T saveAccount(Class<T> accountClass, T account);

}
