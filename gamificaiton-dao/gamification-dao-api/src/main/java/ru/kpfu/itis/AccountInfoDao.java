package ru.kpfu.itis;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountInfoDao extends SimpleDao {
    AccountInfo findByAccount(Account account);

    AccountInfo findById(Long id);

    List<AccountInfo> getAllAndSort(AccountInfo accountInfo);
}
