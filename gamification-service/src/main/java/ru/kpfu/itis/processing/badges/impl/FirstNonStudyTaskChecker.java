package ru.kpfu.itis.processing.badges.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.BadgeConstants;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.enums.TaskCategoryType;
import ru.kpfu.itis.processing.badges.AbstractBadgeChecker;

import java.util.List;

/**
 * Created by ainurminibaev on 14.09.15.
 */
@Component
public class FirstNonStudyTaskChecker extends AbstractBadgeChecker {
    @Override
    public boolean isBadgeApplicable(Account account) {
        List<Task> tasksByUser = taskDao.getTasksByUser(account.getId(), null, null, TaskStatus.TaskStatusType.COMPLETED);
        return tasksByUser
                .stream()
                .filter(it -> it.getCategory() != null)
                .anyMatch(it -> TaskCategoryType.NONSTUDY.equals(it.getCategory().getTaskCategoryType()));
    }

    @Override
    public Badge getBadge() {
        try {
            return simpleDao.findById(Badge.class, BadgeConstants.FIRST_NONSTUDY_BADGE);
        } catch (Exception e) {
            return null;
        }
    }
}
