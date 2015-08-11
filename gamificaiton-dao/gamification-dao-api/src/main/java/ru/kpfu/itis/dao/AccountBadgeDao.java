package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountBadgeDao {

    List<AccountBadge> findAllBadgesByAccount(Account account);

    AccountBadge findByBadgeAndAccount(Badge badge, Account account);
}
