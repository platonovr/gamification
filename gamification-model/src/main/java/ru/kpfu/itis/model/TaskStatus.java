package ru.kpfu.itis.model;

import javax.persistence.*;


@Entity
@Table(name = "TASK_STATUS")
@AttributeOverride(name = "id", column = @Column(name = "TASK_STATUS_ID"))
public class TaskStatus extends BaseLongIdEntity {


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TASK_ID")
    private AccountTask accountTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TASK_HISTORY_ID")
    private AccountTask taskHistory;

    @Column(name = "TASK_STATUS_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatusType type;


    public enum TaskStatusType implements EnumedDictionary {
        ASSIGNED("Назначено"),
        INPROGRESS("Выполняется"),
        CANCELED("Отменено"),
        COMPLETED("Выполено");

        private String caption;

        TaskStatusType(String caption) {
            this.caption = caption;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getCaption() {
            return caption;
        }
    }

    public AccountTask getAccountTask() {
        return accountTask;
    }

    public void setAccountTask(AccountTask accountTask) {
        this.accountTask = accountTask;
    }

    public AccountTask getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(AccountTask taskHistory) {
        this.taskHistory = taskHistory;
    }

    public TaskStatusType getType() {
        return type;
    }

    public void setType(TaskStatusType type) {
        this.type = type;
    }
}
