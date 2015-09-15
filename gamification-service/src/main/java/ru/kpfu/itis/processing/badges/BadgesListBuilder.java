package ru.kpfu.itis.processing.badges;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ainurminibaev on 14.09.15.
 */
public class BadgesListBuilder {
    private BadgesPack badgesPack;
    private List<AbstractBadgeChecker> badgeCheckers;

    public BadgesListBuilder(BadgesPack badgesPack) {
        this.badgesPack = badgesPack;
        this.badgeCheckers = new ArrayList<>();
    }

    public BadgesListBuilder get(Long code) {
        AbstractBadgeChecker badge = badgesPack.getBadge(code);
        if (badge != null) {
            badgeCheckers.add(badge);
        }
        return this;
    }

    public List<AbstractBadgeChecker> build() {
        return badgeCheckers;
    }


}
