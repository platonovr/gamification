package ru.kpfu.itis.model;


import org.hibernate.annotations.CreationTimestamp;
import ru.kpfu.itis.model.enums.TaskStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Rigen on 24.06.15.
 */
@Entity
@Table(name = "ACCOUNT_TASK")
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "ACCOUNT_TASK_ID")),
        @AttributeOverride(name = "createTime", column = @Column(name = "STARTED_AT")),
        @AttributeOverride(name = "finishTime", column = @Column(name = "FINISHED_AT")),
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AccountTask extends BaseLongIdEntity {

    @ManyToOne
    @Column(nullable = false)
    private Account account;

    @OneToOne
    private Task task;

    private boolean availability;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "TASK_STATUS")
    private TaskStatus taskStatus;

    @Column(name = "ATTEMPTS_COUNT")
    private int attemptsCount;

    @Override
    @CreationTimestamp
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(int attemptsCount) {
        this.attemptsCount = attemptsCount;
    }
}
