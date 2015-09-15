package ru.kpfu.itis.processing.badges.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.BadgeConstants;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.processing.badges.AbstractBadgeChecker;

import java.util.List;

/**
 * Created by ainurminibaev on 14.09.15.
 */
@Component
public class Badge5TaskEndChecker extends AbstractBadgeChecker {
    @Override
    public boolean isBadgeApplicable(Account account) {
        List<Task> tasksByUser = taskDao.getTasksByUser(account.getId(), null, null, TaskStatus.TaskStatusType.COMPLETED);
        return tasksByUser.size() >= 5;
    }

    @Override
    public Badge getBadge() {
        try {
            return simpleDao.findById(Badge.class, BadgeConstants.BADGE_5_TASK_END);
        } catch (Exception e) {
            return null;
        }
    }
}
