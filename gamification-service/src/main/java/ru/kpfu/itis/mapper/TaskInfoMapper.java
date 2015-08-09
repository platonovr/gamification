package ru.kpfu.itis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountInfoDto;
import ru.kpfu.itis.dto.TaskInfoDto;
import ru.kpfu.itis.model.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by timur on 13.07.15.
 */
@Component
public class TaskInfoMapper implements Mapper<Task, TaskInfoDto> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public Task fromDto(TaskInfoDto dto) {
        return taskMapper.fromDto(dto);
    }

    @Override
    public TaskInfoDto toDto(Task task) {
        if (task != null) {
            TaskInfoDto taskInfoDto = new TaskInfoDto(taskMapper.toDto(task));
            Set<AcademicGroup> academicGroups = task.getAcademicGroups();
            if (academicGroups != null && !academicGroups.isEmpty())
                taskInfoDto.setGroups(academicGroups.parallelStream()
                        .map(AcademicGroup::getName)
                        .collect(Collectors.toList()));
            Set<AccountTask> taskAccounts = task.getTaskAccounts();
            if (taskAccounts != null && !taskAccounts.isEmpty()) {
                List<AccountInfoDto> performers = taskInfoDto.getPerformers();
                for (AccountTask accountTask : taskAccounts) {
                    TaskStatus taskStatus = accountTask.getTaskStatus();
                    if(taskStatus != null){
                        if (taskStatus.getType().equals(TaskStatus.TaskStatusType.INPROGRESS)) {
                            Account account = accountTask.getAccount();
                            if (account != null) {
                                AccountInfo accountInfo = account.getAccountInfo();
                                if (accountInfo != null)
                                    performers.add(accountInfoMapper.toDto(accountInfo));
                            }
                        }
                    }
                }
            }
            return taskInfoDto;
        }
        return null;
    }
}
