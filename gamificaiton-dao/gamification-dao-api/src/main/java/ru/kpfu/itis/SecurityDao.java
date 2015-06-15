package ru.kpfu.itis;


import ru.kpfu.itis.model.Account;

import java.util.UUID;

/**
 * @date 30.04.14
 */
public interface SecurityDao {

    Account getAccount(UUID accountId);

    Account findAccount(String login);

    <T extends Account> T saveAccount(Class<T> accountClass, T account);

}
