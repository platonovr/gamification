package ru.kpfu.itis.dto;

/**
 * Created by ainurminibaev on 13.08.15.
 */
public class TaskEnrollDto extends ErrorDto {
    private TaskEnrollStatus status;

    public TaskEnrollDto(ru.kpfu.itis.dto.enums.Error error) {
        super(error);
    }

    public TaskEnrollDto(ru.kpfu.itis.dto.enums.Error error, TaskEnrollStatus status) {
        super(error);
        this.status = status;
    }

    public TaskEnrollDto(TaskEnrollStatus status) {
        super(null);
        this.status = status;
    }

    public TaskEnrollStatus getStatus() {
        return status;
    }

    public void setStatus(TaskEnrollStatus status) {
        this.status = status;
    }

    public enum TaskEnrollStatus {
        SUCCESS, FAILED
    }
}
