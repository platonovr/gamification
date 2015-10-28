package ru.kpfu.itis.mapper;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AccountInfoDto;
import ru.kpfu.itis.dto.TaskInfoDto;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.service.FileService;
import ru.kpfu.itis.util.Utils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by timur on 13.07.15.
 */
@Component("studentTaskInfoMapper")
public class TaskInfoMapper implements Mapper<Task, TaskInfoDto> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private FileDtoMapper fileDtoMapper;

    @Autowired
    private FileService fileService;

    private boolean isAdmin;

    public TaskInfoMapper() {
        isAdmin = false;
    }

    public TaskInfoMapper(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public Task fromDto(TaskInfoDto dto) {
        return taskMapper.fromDto(dto);
    }

    @Override
    public TaskInfoDto toDto(Task task) {
        if (task != null) {
            TaskInfoDto taskInfoDto = new TaskInfoDto(taskMapper.toDto(task));
            if (isAdmin) {
                taskInfoDto.setStatusMap(new HashMap<>());
            }
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
                    if (taskStatus != null) {
                        if (taskStatus.getType().equals(TaskStatus.TaskStatusType.COMPLETED) || isAdmin) {
                            Account account = accountTask.getAccount();
                            if (account != null) {
                                AccountInfo accountInfo = account.getAccountInfo();
                                if (accountInfo != null) {
                                    AccountInfoDto accountInfoDto = accountInfoMapper.toDto(accountInfo);
                                    accountInfoDto.setTimeBack(Utils.timeToString(accountTask.getChangeTime()));
                                    performers.add(accountInfoDto);
                                }
                                if (isAdmin) {
                                    taskInfoDto.getStatusMap().put(account.getId(), new TaskInfoDto.StatusMark(taskStatus.getType().name(), accountTask.getMark()));
                                }
                            }
                        }
                    }
                }
            }
            if (!isAdmin) {
                List<File> taskFiles = fileService.getTaskFiles(task.getId());
                taskInfoDto.setFiles(taskFiles
                        .stream()
                        .map(it -> fileDtoMapper.toDto(it, task))
                        .collect(Collectors.toList()));
            }
            return taskInfoDto;
        }
        return null;
    }
}
