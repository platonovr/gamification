package ru.kpfu.itis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountInfoDto;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.hibernate.Hibernate.isInitialized;

/**
 * Created by timur on 30.06.15.
 */
@Component
public class TaskMapper implements Mapper<Task, TaskDto> {

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Override
    public Task fromDto(TaskDto taskDto) {
        Task task = new Task();
//        task.setCategory(taskCategoryDao.findByName(taskDto.getCategory()));
//        task.setAuthor(accountDao.findByLogin("admin"));
        task.setName(taskDto.getName());
        task.setMaxMark(taskDto.getMaxMark());
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
            taskDto.setId(task.getId());
            taskDto.setName(task.getName());
            taskDto.setDescription(task.getDescription());
            taskDto.setMaxPerformers(task.getParticipantsCount());
            TaskCategory category = task.getCategory();
            if (isInitialized(category))
                taskDto.setCategory(ofNullable(category).map(TaskCategory::getName).orElse(null));
            Account author = task.getAuthor();
            if (isInitialized(author))
                taskDto.setCreator(ofNullable(author).map(Account::getLogin).orElse(null));
            Badge badge = task.getBadge();
            if (isInitialized(badge) && badge != null)
                taskDto.setBadge(new BadgeDto(badge.getId(),
                        badge.getName(), badge.getImage(),
                        badge.getType().name(), badge.getDescription()));
            Set<AcademicGroup> academicGroups = task.getAcademicGroups();
            if (isInitialized(academicGroups) && academicGroups != null && !academicGroups.isEmpty())
                taskDto.setGroups(academicGroups.parallelStream()
                        .map(AcademicGroup::getName)
                        .collect(Collectors.toList()));
            List<AccountTask> taskAccounts = task.getTaskAccounts();
            if (isInitialized(taskAccounts) && taskAccounts != null && !taskAccounts.isEmpty()) {
                List<AccountInfoDto> performers = taskDto.getPerformers();
                for (AccountTask accountTask : taskAccounts)
                    if (accountTask.getTaskStatus().getType().equals(TaskStatus.TaskStatusType.INPROGRESS)) {
                        Account account = accountTask.getAccount();
                        if (account != null) {
                            AccountInfo accountInfo = account.getAccountInfo();
                            if (accountInfo != null)
                                performers.add(accountInfoMapper.toDto(accountInfo));
                        }
                    }
            }
            taskDto.setMaxMark(task.getMaxMark());
            taskDto.setStartDate(task.getStartDate());
            taskDto.setDeadline(task.getEndDate());
            taskDto.setCreateTime(task.getCreateTime());
            taskDto.setChangeTime(task.getChangeTime());
            return taskDto;
        }
        return null;
    }
}
