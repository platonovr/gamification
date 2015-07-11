package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
public interface AccountBadgeService {
    List<AccountBadge> findAllBadgesByAccount(Account account);

    @Transactional
    AccountBadge findByBadgeAndAccount(Badge badge, Account account);

    @Transactional
    void saveOrUpdate(AccountBadge accountBadge);
}
