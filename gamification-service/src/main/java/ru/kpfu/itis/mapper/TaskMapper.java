package ru.kpfu.itis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountInfoDto;
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
            taskDto.setCategory(Optional.ofNullable(task.getCategory()).<String>map(TaskCategory::getName).orElse(null));
            Account author = task.getAuthor();
            if (author != null)
                taskDto.setCreator(author.getLogin());
            taskDto.setGroups(Optional.ofNullable(task.getAcademicGroups())
                    .<List<String>>map(academicGroups -> academicGroups.parallelStream()
                            .<String>map(AcademicGroup::getName)
                            .collect(Collectors.toList()))
                    .orElse(null));
            List<AccountTask> taskAccounts = task.getTaskAccounts();
            Long id;
            String firstName, lastName, group;
            if (taskAccounts != null) {
                List<String> performerNames = taskDto.getPerformerNames();
                List<AccountInfoDto> performers = taskDto.getPerformers();
                for (AccountTask accountTask : taskAccounts) {
                    if (accountTask.getTaskStatus().getType().equals(TaskStatus.TaskStatusType.INPROGRESS)) {
                        Account account = accountTask.getAccount();
                        if (account != null) {
                            AccountInfo accountInfo = account.getAccountInfo();
                            if (accountInfo != null) {
                                firstName = accountInfo.getFirstName();
                                lastName = accountInfo.getLastName();
                                group = Optional.ofNullable(accountInfo.getGroup()).map(AcademicGroup::getName).orElse(null);
                                performerNames.add(firstName + " " + lastName + ", " + group);
                                performers.add(accountInfoMapper.toDto(accountInfo));
                            }
                        }
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

//    private <T,L> T optionalField(T object)
}
