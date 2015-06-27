package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dao.AccountInfoDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.service.AccountInfoService;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoDao accountInfoDao;

    @Override
    public AccountInfo findByAccount(Account account) {
        return accountInfoDao.findByAccount(account);
    }

    @Override
    public AccountInfo findById(Long id) {
        return accountInfoDao.findById(id);
    }

    @Override
    public List<AccountInfo> getAllAndSort(AccountInfo accountInfo) {
        return accountInfoDao.getAllAndSort(accountInfo);
    }
}
