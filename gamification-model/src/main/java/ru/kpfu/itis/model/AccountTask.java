package ru.kpfu.itis.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Roman on 26.06.2015.
 */

@Entity
@Table(name = "ACCOUNT_TASK")
@AttributeOverride(name = "id", column = @Column(name = "ACCOUNT_TASK_ID"))
public class AccountTask extends BaseLongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", nullable = false)
    private Task task;

    @Column(name = "AVAILABILITY", nullable = false)
    private Boolean availability;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountTask")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TaskStatus taskStatus;

    @Transient
    private TaskStatus oldTaskStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskHistory")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<TaskStatus> taskHistory = new HashSet<>();

    private Integer mark;

    @Column(name = "ATTEMPTS_COUNT")
    public Integer attemptsCount;

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * Вызывать из транзакции, либо отдельно сохранять старый статус
     *
     * @param taskStatus новый статус
     */
    public void setNewStatus(TaskStatus taskStatus) {
        getTaskHistory().add(taskStatus);
        taskStatus.setTaskHistory(this);

        // current status
        if (this.taskStatus != null) {
            this.oldTaskStatus = this.taskStatus;
            this.oldTaskStatus.setAccountTask(null);
        }

        this.taskStatus = taskStatus;
        this.taskStatus.setAccountTask(this);
    }

    public Set<TaskStatus> getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(Set<TaskStatus> taskHistory) {
        this.taskHistory = taskHistory;
    }

    public Integer getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(Integer attemptsCount) {
        this.attemptsCount = attemptsCount;
    }

    public TaskStatus getOldTaskStatus() {
        return oldTaskStatus;
    }

}
