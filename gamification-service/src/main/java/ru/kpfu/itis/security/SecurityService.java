package ru.kpfu.itis.security;

import ru.kpfu.itis.model.Account;


public interface SecurityService {

    Account getCurrentUser();

    Long getCurrentUserId();

}
