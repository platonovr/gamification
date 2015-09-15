package ru.kpfu.itis.processing.badges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.model.enums.BadgeAchievementStatus;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.AccountBadgeService;

/**
 * Created by ainurminibaev on 14.09.15.
 */
public abstract class AbstractBadgeChecker {

    @Autowired
    protected BadgesPack badgesPack;

    @Autowired
    protected SimpleDao simpleDao;

    @Autowired
    protected SecurityService securityService;

    @Autowired
    protected AccountBadgeService accountBadgeService;

    @Autowired
    protected TaskDao taskDao;

    @Transactional
    public void assignBadge() {
        assignBadgeFor(securityService.getCurrentUser());
    }

    public void assignBadgeFor(Account account){
        Badge badge = getBadge();
        if (badge == null || account == null) {
            return;
        }
        if (isBadgeApplicable(account)) {
            AccountBadge accountBadge = accountBadgeService.findByBadgeAndAccount(badge, account);
            if (accountBadge != null) {
                if (BadgeAchievementStatus.COMPLETE.equals(accountBadge.getAchevementStatus())) {
                    return;
                }
            } else {
                accountBadge = accountBadgeService.createAccountBadge(badge, account);
            }
            accountBadge.setAchevementStatus(BadgeAchievementStatus.COMPLETE);
            simpleDao.save(accountBadge);
        }
    }

    public abstract boolean isBadgeApplicable(Account account);

    public abstract Badge getBadge();
}
