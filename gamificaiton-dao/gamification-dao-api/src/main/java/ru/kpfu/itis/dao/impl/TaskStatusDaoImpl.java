package ru.kpfu.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.TaskStatusDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.TaskStatus;

/**
 * Created by ainurminibaev on 07.08.15.
 */
@Repository("taskStatusDao")
public class TaskStatusDaoImpl extends AbstractDaoImpl<TaskStatus, Long> implements TaskStatusDao {

    protected TaskStatusDaoImpl() {
        super(TaskStatus.class);
    }

}
