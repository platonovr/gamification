package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            taskDto.setCategory(Optional.ofNullable(task.getCategory()).<String>map(TaskCategory::getName).orElse(null));
            taskDto.setCreator(Optional.ofNullable(task.getAuthor()).<String>map(Account::getLogin).orElse(null));
            taskDto.setGroups(Optional.ofNullable(task.getAcademicGroups())
                    .<List<String>>map(academicGroups -> academicGroups.parallelStream()
                            .<String>map(AcademicGroup::getName)
                            .collect(Collectors.toList()))
                    .orElse(null));
            taskDto.setPerformers(Optional.ofNullable(task.getTaskAccounts())
                    .<List<String>>map(accountTasks -> accountTasks.parallelStream()
                            .<String>map(accountTask -> {
                                Account account = accountTask.getAccount();
                                if (account != null) {
                                    AccountInfo accountInfo = account.getAccountInfo();
                                    if (accountInfo != null) {
                                        String firstName = accountInfo.getFirstName();
                                        String lastName = accountInfo.getLastName();
                                        String group = Optional.ofNullable(accountInfo.getGroup()).map(AcademicGroup::getName).orElse(null);
                                        return firstName + " " + lastName + ", " + group;
                                    }
                                }
                                return null;
                            }).collect(Collectors.toList()))
                    .orElse(null));
            taskDto.setMaxMark(task.getMaxMark());
            taskDto.setStartDate(task.getStartDate());
            taskDto.setDeadline(task.getEndDate());
            taskDto.setCreateTime(task.getCreateTime());
            taskDto.setChangeTime(task.getChangeTime());
            return taskDto;
        }
        return null;
    }

//    private <T,L> T optionalField(T object)
}
