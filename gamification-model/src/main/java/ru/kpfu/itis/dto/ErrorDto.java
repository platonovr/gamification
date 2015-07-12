package ru.kpfu.itis.dto;

import ru.kpfu.itis.dto.enums.Error;

/**
 * Created by timur on 10.07.15.
 */
public class ErrorDto {

    private String msg;

    private Integer code;

    public ErrorDto(Error error) {
        msg = error.getMsg();
        code = error.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
