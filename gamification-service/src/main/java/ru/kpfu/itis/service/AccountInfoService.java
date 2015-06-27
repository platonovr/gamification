package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountInfoService {
    AccountInfo findByAccount(Account account);

    AccountInfo findById(Long id);

    List<AccountInfo> getAllAndSort(AccountInfo accountInfo);
}
