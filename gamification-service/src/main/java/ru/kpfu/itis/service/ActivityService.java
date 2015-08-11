package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.model.Activity;

/**
 * Created by Rigen on 20.07.15.
 */
public interface ActivityService {
    @Transactional
    void save(Activity activity);
}
