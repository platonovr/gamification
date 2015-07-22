package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.classifier.TaskCategory;
import ru.kpfu.itis.model.enums.TaskCategoryType;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Roman on 27.06.2015.
 */
public final class TestToolkit {

    public static Account fakeAccount() {
        Account account = new Account();
        account.setCreateTime(new Date());
        account.setLogin(UUID.randomUUID().toString());
        account.setPassword("p@ssw0rd");
        return account;
    }

    public static TaskCategory fakeTaskCategory() {
        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setName("category");
        taskCategory.setTaskCategoryType(TaskCategoryType.STUDY);
        taskCategory.setCreateTime(new Date());
        return taskCategory;
    }

    public static Task fakeTask() {
        Task task = new Task();
        task.setCreateTime(new Date());
        task.setName("taskName");
        task.setDescription("description");
        task.setType(Task.TaskType.PERSONAL);
        task.setMaxMark((byte) 1);
        task.setParticipantsCount(10);
        return task;
    }
}

