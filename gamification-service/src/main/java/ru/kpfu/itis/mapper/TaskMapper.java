package ru.kpfu.itis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dao.AccountTaskDao;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.classifier.TaskCategory;
import ru.kpfu.itis.model.enums.Role;
import ru.kpfu.itis.security.SecurityService;

import java.util.Date;

import static java.util.Optional.ofNullable;
import static org.hibernate.Hibernate.isInitialized;

/**
 * Created by timur on 30.06.15.
 */
@Component
public class TaskMapper implements Mapper<Task, TaskDto> {

    private final long threeDaysInMillis = 3 * 24 * 60 * 60 * 1000;

    @Autowired
    private AccountTaskDao accountTaskDao;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SecurityService securityService;

    @Override
    public Task fromDto(TaskDto taskDto) {
        Task task = new Task();
//        task.setCategory(taskCategoryDao.findByName(taskDto.getCategory()));
//        task.setAuthor(accountDao.findByLogin("admin"));
        task.setName(taskDto.getName());
        task.setMaxMark(ofNullable(taskDto.getMaxMark()).map(Integer::byteValue).orElse(null));
        task.setDescription(taskDto.getDescription());
        task.setStartDate(taskDto.getStartDate());
        task.setEndDate(taskDto.getDeadline());
        task.setCreateTime(taskDto.getCreateTime());
        task.setChangeTime(taskDto.getChangeTime());
        return task;
    }

    @Override
    public TaskDto toDto(Task task) {
        if (task != null) {
            TaskDto taskDto = new TaskDto();
            Long taskId = task.getId();
            taskDto.setId(taskId);
            taskDto.setName(task.getName());
            taskDto.setDescription(task.getDescription());
            Subject subject = task.getSubject();
            if (isInitialized(subject) && subject != null)
                taskDto.setSubject(subjectMapper.toDto(subject));
            taskDto.setMaxPerformers(task.getParticipantsCount());
            Account currentUser = securityService.getCurrentUser();
            if (currentUser != null && currentUser.getRole().equals(Role.STUDENT)) {
                AccountTask accountTask = accountTaskDao.findByTaskIdAndAccountId(taskId, currentUser.getId());
                if (accountTask == null) {
                    taskDto.setStatus("NOT_STARTED");
                    taskDto.setCurrentMark(0);
                } else {
                    taskDto.setCurrentMark(ofNullable(accountTask.getMark()).orElse(0));
                    TaskStatus taskStatus = accountTask.getTaskStatus();
                    if (isInitialized(taskStatus) && taskStatus != null)
                        taskDto.setStatus(taskStatus.getType().name());
                }
            }
            TaskCategory category = task.getCategory();
            if (isInitialized(category))
                taskDto.setCategory(ofNullable(category).map(TaskCategory::getName).orElse(null));
            Account author = task.getAuthor();
            if (isInitialized(author))
                taskDto.setCreator(ofNullable(author).map(Account::getLogin).orElse(null));
            Badge badge = task.getBadge();
            if (isInitialized(badge) && badge != null) {
                Subject badgeSubject = badge.getSubject();
                if (isInitialized(badgeSubject) && badgeSubject != null)
                    taskDto.setBadge(new BadgeDto(badge.getId(),
                            badge.getName(), badge.getImage(), subjectMapper.toDto(badgeSubject),
                            badge.getType().name(), badge.getDescription()));
                else
                    taskDto.setBadge(new BadgeDto(badge.getId(),
                            badge.getName(), badge.getImage(),
                            badge.getType().name(), badge.getDescription()));
            }
            taskDto.setMaxMark(Integer.valueOf(task.getMaxMark()));
            taskDto.setStartDate(task.getStartDate());
            taskDto.setDeadline(task.getEndDate());
            Date createTime = task.getCreateTime();
            taskDto.setCreateTime(createTime);
            taskDto.setChangeTime(task.getChangeTime());
            //TODO think about task's labels
            if (createTime != null && createTime.getTime() >= new Date().getTime() - threeDaysInMillis) {
                taskDto.getLabels().add("HOT");
            }
            return taskDto;
        }
        return null;
    }
}
