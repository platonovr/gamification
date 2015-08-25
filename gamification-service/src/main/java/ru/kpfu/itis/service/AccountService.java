package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.jbl.auth.service.UserServiceAuth;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountService extends UserServiceAuth {
    Account findById(Long id);

    Account createAnonymousUser(String login);
}
