package ru.kpfu.itis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.dao.TaskDao;
import ru.kpfu.itis.dto.TaskCategoryDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.dto.TaskInfoDto;
import ru.kpfu.itis.mapper.TaskInfoMapper;
import ru.kpfu.itis.mapper.TaskMapper;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskStatus;
import ru.kpfu.itis.model.classifier.TaskCategory;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by timur on 17.06.15.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {


    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskCategoryDao taskCategoryDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Autowired
    private SecurityService securityService;

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
        task.setAuthor(securityService.getCurrentUser());
        taskDao.save(task);
        return task;
    }

    @Override
    @Transactional
    public Task findByName(String name) {
        return taskDao.findByField(Task.class, "name", name);
    }

    @Override
    @Transactional
    public Task findTaskById(Long id) {
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
    @Transactional
    public List<Task> getActualTasks() {
        return taskDao.getActualTasks();
    }

    @Override
    @Transactional
    public List<Task> getTasksByUser(Long userId) {
        return taskDao.getTasksByUser(userId);
    }

    @Override
    public TaskInfoDto findById(Long taskId) {
        Task task = taskDao.findById(taskId);
        return taskInfoMapper.toDto(task);
    }

    @Override
    @Transactional
    public List<TaskDto> getTasksByUser(Long userId, Integer offset, Integer limit, TaskStatus.TaskStatusType status) {
        List<Task> tasksByUser = taskDao.getTasksByUser(userId, offset, limit, status);
        return tasksByUser.parallelStream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void stub() {
        System.out.println("hello,hello,hello");
    }

    @Override
    @Transactional
    public List<TaskInfoDto> getCreatedTasks(Long userId, Integer offset, Integer limit) {
        List<Task> createdTasks = taskDao.getCreatedTasks(userId, offset, limit);
        return createdTasks.stream().map(taskInfoMapper::toDto).collect(Collectors.toList());
    }


}
