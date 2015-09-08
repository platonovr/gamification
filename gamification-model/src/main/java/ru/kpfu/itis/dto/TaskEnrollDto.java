package ru.kpfu.itis.dto;

import ru.kpfu.itis.dto.enums.Responses;

/**
 * Created by ainurminibaev on 13.08.15.
 */
public class TaskEnrollDto extends ResponseDto {
    private TaskEnrollStatus status;

    public TaskEnrollDto(Responses error) {
        super(error);
    }

    public TaskEnrollDto(Responses error, TaskEnrollStatus status) {
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
