package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.processing.badges.AbstractBadgeChecker;

import java.util.List;

/**
 * Created by Rigen on 25.06.15.
 */
public interface AccountBadgeService {

    List<AccountBadge> findAllBadgesByAccount(Account account);

    AccountBadge findByBadgeAndAccount(Badge badge, Account account);

    @Transactional
    AccountBadge createAccountBadge(Badge badge, Account currentUser);

    void saveOrUpdate(AccountBadge accountBadge);

    void applyBadges(List<AbstractBadgeChecker> badgeCheckers, Account account);
}
