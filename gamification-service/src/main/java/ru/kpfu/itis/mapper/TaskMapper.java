package ru.kpfu.itis.mapper;

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
//                List<String> performerNames = taskDto.getPerformerNames();
                List<AccountInfoDto> performers = taskDto.getPerformers();
                for (AccountTask accountTask : taskAccounts) {
                    ofNullable(accountTask.getAccount())
                            .ifPresent(account -> ofNullable(account.getAccountInfo())
                                    .ifPresent(accountInfo -> performers.add(new AccountInfoDto(account.getId(),
                                            accountInfo.getFirstName(),
                                            accountInfo.getLastName(),
                                            ofNullable(accountInfo.getGroup())
                                                    .map(AcademicGroup::getName).orElse(null)))));
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
