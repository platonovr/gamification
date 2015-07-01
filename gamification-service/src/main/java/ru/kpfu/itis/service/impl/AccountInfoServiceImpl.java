package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public AccountInfo findByAccount(Account account) {
        return accountInfoDao.findByAccount(account);
    }

    @Transactional
    @Override
    public AccountInfo findByAccountId(Long id) {
        return accountInfoDao.findByAccountId(id);
    }

    @Transactional
    @Override
    public List<AccountInfo> getAllAndSort(AccountInfo accountInfo) {
        return accountInfoDao.getAllAndSort(accountInfo);
    }
}
