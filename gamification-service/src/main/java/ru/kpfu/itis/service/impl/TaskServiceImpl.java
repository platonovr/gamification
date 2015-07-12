package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dto.TaskCategoryDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.mapper.TaskMapper;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskCategory;
import ru.kpfu.itis.model.TaskStatus;
import ru.kpfu.itis.service.TaskService;

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
    public Task findById(Long id) {
        return taskDao.findById(Task.class, id);
    }

    @Override
    public List<TaskCategoryDto> getAllCategories() {
        return taskCategoryDao.fetchAll(TaskCategory.class)
                .parallelStream().<TaskCategoryDto>map(taskCategory ->
                        new TaskCategoryDto(taskCategory.getName(), taskCategory.getTaskCategoryType()))
                .collect(Collectors.toList());
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
    public TaskDto findById(Long taskId) {
        Task task = taskDao.findById(taskId);
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getTasksByUser(Long userId, Integer offset, Integer limit, TaskStatus.TaskStatusType status) {
        List<Task> tasksByUser = taskDao.getTasksByUser(userId, offset, limit, status);
        return tasksByUser.parallelStream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TaskDto> getCreatedTasks(Long userId, Integer offset, Integer limit) {
        List<Task> createdTasks = taskDao.getCreatedTasks(userId, offset, limit);
        return createdTasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }


}
