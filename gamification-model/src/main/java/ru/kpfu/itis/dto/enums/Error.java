package ru.kpfu.itis.dto.enums;

/**
 * Created by timur on 10.07.15.
 */
public enum Error {

    USER_NOT_FOUND("User with requested id not found", 100),
    USER_INFO_NOT_FOUND("Information for user with requested id not found", 101),
    TASK_NOT_FOUND("Task doesn't found", 102),
    EMPTY_FILE("file is empty", 103);

    private String msg;
    private int code;

    Error(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
