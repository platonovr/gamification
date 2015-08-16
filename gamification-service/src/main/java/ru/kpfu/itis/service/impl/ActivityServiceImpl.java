package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.ActivityDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Activity;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.ActivityService;
import ru.kpfu.itis.service.TaskService;

import java.util.List;

/**
 * Created by Rigen on 20.07.15.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;
    @Autowired
    SimpleDao simpleDao;
    @Autowired
    SecurityService securityService;
    @Autowired
    TaskService taskService;

    @Override
    @Transactional
    public void save(Activity activity) {
        simpleDao.save(activity);
    }

    @Override
    @Transactional
    public List<Activity> getActivityStream() {
        Account account = securityService.getCurrentUser();
        List<Task> tasks = taskService.getTasksByUser(account.getId());
        return activityDao.getActivityStream(account, tasks);
    }

    @Override
    @Transactional
    public List<Activity> getActivityStreamDao() {
        Account account = securityService.getCurrentUser();
        List<Task> tasks = taskService.getTasksByUser(account.getId());
        return activityDao.getActivityStream(account, tasks);
    }
}
