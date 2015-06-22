package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountInfoService {
    AccountInfo findByAccount(Account account);

}
