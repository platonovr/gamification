package ru.kpfu.itis.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountBadgeDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.service.AccountBadgeService;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
@Service
public class AccountBadgeServiceImpl implements AccountBadgeService {

    @Autowired
    private SimpleDao simpleDao;

    @Autowired
    private AccountBadgeDao accountBadgeDao;

    @Override
    @Transactional
    public List<AccountBadge> findAllBadgesByAccount(Account account) {
        List<AccountBadge> allBadgesByAccount = accountBadgeDao.findAllBadgesByAccount(account);
        allBadgesByAccount.stream().filter(it -> it.getBadge() != null).forEach(it -> Hibernate.initialize(it.getBadge().getTasks()));
        return allBadgesByAccount;
    }

    @Transactional
    @Override
    public AccountBadge findByBadgeAndAccount(Badge badge, Account account) {
        return accountBadgeDao.findByBadgeAndAccount(badge, account);
    }

    @Override
    @Transactional
    public AccountBadge createAccountBadge(Badge badge, Account currentUser) {
        AccountBadge notExistedAccountBadge = new AccountBadge();
        notExistedAccountBadge.setAccount(currentUser);
        notExistedAccountBadge.setBadge(badge);
        simpleDao.save(notExistedAccountBadge);
        return notExistedAccountBadge;
    }

    @Transactional
    @Override
    public void saveOrUpdate(AccountBadge accountBadge) {
        simpleDao.saveOrUpdate(accountBadge);
    }
}
