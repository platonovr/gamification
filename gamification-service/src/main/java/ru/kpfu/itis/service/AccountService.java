package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountService {
    Account findById(Long id);

}
