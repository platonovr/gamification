package ru.kpfu.itis.processing.badges;

/**
 * Created by ainurminibaev on 14.09.15.
 */
public interface BadgesPack {

    AbstractBadgeChecker getBadge(Long code);
}
