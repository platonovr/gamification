package ru.kpfu.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.classifier.TaskCategory;

/**
 * Created by timur on 24.06.15.
 */
@Repository("taskCategoryDao")
public class TaskCategoryDaoImpl extends AbstractDaoImpl<TaskCategory, Long> implements TaskCategoryDao {
    protected TaskCategoryDaoImpl() {
        super(TaskCategory.class);
    }

    @Override
    public TaskCategory findByName(String name) {
        return findByField("name", name);
    }

}
