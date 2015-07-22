package ru.kpfu.itis.security;

import ru.kpfu.itis.model.Account;


public interface SecurityService {


    Account getAccount(Long accountId);

    <T extends Account> T saveAccount(Class<T> accountClass, T account);

    Account getCurrentUser();

    Long getCurrentUserId();

}
