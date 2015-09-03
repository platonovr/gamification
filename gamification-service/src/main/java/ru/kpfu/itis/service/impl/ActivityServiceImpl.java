package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.ActivityDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dto.ActivityDto;
import ru.kpfu.itis.mapper.ActivityMapper;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Activity;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.ActivityService;

import java.util.List;
import java.util.stream.Collectors;

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
    ActivityMapper activityMapper;
    @Autowired
    TaskDao taskDao;

    @Override
    @Transactional
    public void save(Activity activity) {
        simpleDao.save(activity);
    }

    @Override
    @Transactional
    public List<ActivityDto> getActivityStream(Long lastActivityId) {
        Account account = securityService.getCurrentUser();
        List<Task> tasks = taskDao.getTasksByUser(account.getId(), null, null, null);
        return activityDao.getActivityStream(account, tasks, lastActivityId)
                .stream()
                .map(activityMapper::toDto)
                .collect(Collectors.toList());
    }
}
