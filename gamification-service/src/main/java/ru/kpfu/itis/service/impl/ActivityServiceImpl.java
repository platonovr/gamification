package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.ActivityDao;
import ru.kpfu.itis.model.Activity;
import ru.kpfu.itis.service.ActivityService;

/**
 * Created by Rigen on 20.07.15.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    @Override
    @Transactional
    public void save(Activity activity) {
        activityDao.save(activity);
    }
}
