package ru.kpfu.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.model.TaskCategory;

/**
 * Created by timur on 24.06.15.
 */
@Repository
public class TaskCategoryDaoImpl extends SimpleDaoImpl implements TaskCategoryDao {
    @Override
    public TaskCategory findByName(String name) {
        return findByField(TaskCategory.class, "name", name);
    }
}
