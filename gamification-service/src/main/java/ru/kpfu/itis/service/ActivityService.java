package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.model.Activity;

import java.util.List;

/**
 * Created by Rigen on 20.07.15.
 */
public interface ActivityService {
    @Transactional
    void save(Activity activity);

    @Transactional
    List<Activity> getActivityStream();

    @Transactional
    List<Activity> getActivityStreamDao();
}
