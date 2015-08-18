package ru.kpfu.itis.mapper;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dto.ActivityDto;
import ru.kpfu.itis.model.*;

/**
 * Created by ainurminibaev on 18.08.15.
 */
@Component
public class ActivityMapper implements Mapper<Activity, ActivityDto> {

    @Autowired
    SimpleDao simpleDao;

    @Autowired
    MessageSource messageSource;


    @Override
    public Activity fromDto(ActivityDto dto) {
        return null;
    }

    @Override
    public ActivityDto toDto(Activity activity) {
        Hibernate.initialize(activity.getAccount().getAccountInfo());
        ActivityDto activityDto = new ActivityDto();
        Account account = activity.getAccount();
        AccountInfo accountInfo = account.getAccountInfo();
        activityDto.setId(activity.getId());
        activityDto.setUserId(account.getId());
        activityDto.setFirstName(accountInfo.getFirstName());
        activityDto.setLastName(accountInfo.getLastName());
        activityDto.setPhoto(accountInfo.getPhoto());
        String msg = "";
        switch (activity.getEntityType()) {
            case BADGE:
                Badge badge = simpleDao.findById(Badge.class, activity.getEntityId());
                Object[] args = {accountInfo.getFirstName(), accountInfo.getLastName(), badge.getName()};
                msg = getMsgForBadge(activity, args);
                break;
            case TASK:
                Task task = simpleDao.findById(Task.class, activity.getEntityId());
                Object[] taskArgs = {accountInfo.getFirstName(), accountInfo.getLastName(), task.getName()};
                msg = getMsgForTask(activity, taskArgs);
                break;
        }
        activityDto.setActivity(msg);
        return activityDto;
    }

    private String getMsgForTask(Activity activity, Object[] taskArgs) {
        switch (activity.getActivityType()) {
            case TASK_NEW:
                return messageSource.getMessage("task.creation", taskArgs, null);
            case TASK_ENROLL:
                return messageSource.getMessage("task.start", taskArgs, null);
            case TASK_COMPLETE:
                return messageSource.getMessage("task.end", taskArgs, null);
            default:
                return "";
        }
    }

    private String getMsgForBadge(Activity activity, Object[] args) {
        switch (activity.getActivityType()) {
            case BADGE_COMPLETE:
                return messageSource.getMessage("badge.get", args, null);
            default:
                return "";
        }
    }
}
