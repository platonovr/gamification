package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Activity;
import ru.kpfu.itis.model.Task;

import java.util.List;

/**
 * Created by Rigen on 20.07.15.
 */
public interface ActivityDao {

    List<Activity> getActivityStream(Account account, List<Task> tasks);
}
