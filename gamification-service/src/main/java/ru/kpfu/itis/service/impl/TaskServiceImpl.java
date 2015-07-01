package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.mapper.TaskMapper;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;
import ru.kpfu.itis.service.TaskService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by timur on 17.06.15.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskCategoryDao taskCategoryDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    @Transactional
    public Task submitTask(Task task) {
        return taskDao.submitTask(task);
    }

    //    @RolesAllowed({"ADMIN", "TEACHER"})
    @Override
    @Transactional
    public Task save(TaskDto taskDto) {
        Task task = taskMapper.fromDto(taskDto);
        task.setCategory(taskCategoryDao.findByName(taskDto.getCategory()));
        //TODO replace with getAuthUser() when we will make authentication
        task.setAuthor(accountDao.findByLogin("admin"));
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

    @Override
    @Transactional
    public TaskCategory save(TaskCategory taskCategory) {
        return taskCategoryDao.save(taskCategory);
    }

    @Override
    public List<Task> getActualTasks() {
        return taskDao.getActualTasks();
    }

    @Override
    public List<Task> getTasksByUser(Long userId) {
        return taskDao.getTasksByUser(userId);
    }

    @Override
    @Transactional
    public List<TaskDto> getAvailableTasksByUser(Long userId, Integer offset, Integer maxResult) {
        List<Task> availableTasksByUser = taskDao.getAvailableTasksByUser(userId, offset, maxResult);
        return availableTasksByUser.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }


}
