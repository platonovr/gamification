package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.TaskCategory;

/**
 * Created by timur on 24.06.15.
 */
public interface TaskCategoryDao extends SimpleDao {

    TaskCategory findByName(String name);

    TaskCategory save(TaskCategory taskCategory);
}
