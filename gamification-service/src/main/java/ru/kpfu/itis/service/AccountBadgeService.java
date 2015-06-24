package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
public interface AccountBadgeService {
    List<AccountBadge> findAllBadgesByAccount(Account account);
}
