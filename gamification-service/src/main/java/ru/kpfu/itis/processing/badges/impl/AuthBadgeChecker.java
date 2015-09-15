package ru.kpfu.itis.processing.badges.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.BadgeConstants;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.processing.badges.AbstractBadgeChecker;

/**
 * Created by ainurminibaev on 14.09.15.
 */
@Component
public class AuthBadgeChecker extends AbstractBadgeChecker {
    @Override
    public boolean isBadgeApplicable(Account account) {
        return true;
    }

    @Override
    public Badge getBadge() {
        return simpleDao.findById(Badge.class, BadgeConstants.AUTH_BADGE_ID);
    }
}
