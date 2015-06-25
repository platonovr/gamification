package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;
import ru.kpfu.itis.service.TaskService;

import java.util.Collection;
import java.util.Date;

/**
 * Created by timur on 17.06.15.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskCategoryDao taskCategoryDao;

    @Autowired
    private AccountDao accountDao;

    //    @RolesAllowed({"ADMIN", "TEACHER"})
    @Override
    public Task save(TaskDTO taskDTO) {
        Task task = new Task();
        task.setCategory(taskCategoryDao.findByName(taskDTO.getCategory()));
        //TODO replace with getAuthUser() when we will make authentication
        task.setAuthor(accountDao.findByLogin("admin"));
        task.setName(taskDTO.getName());
        task.setMaxMark(taskDTO.getMaxMark());
        task.setFinishTime(taskDTO.getDeadline());
        task.setDescription(taskDTO.getDescription());
        task.setCreateTime(new Date());
        taskDao.save(task);
        return task;
    }

    @Override
    public Task findByName(String name) {
        return taskDao.findByField(Task.class, "name", name);
    }

    @Override
    public Collection<TaskCategory> getAllCategories() {
        return taskCategoryDao.fetchAll(TaskCategory.class);
    }

}
