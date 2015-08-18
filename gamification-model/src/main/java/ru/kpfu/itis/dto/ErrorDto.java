package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.kpfu.itis.dto.enums.Error;

/**
 * Created by timur on 10.07.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {

    private String msg;

    private Integer code;

    public ErrorDto(Error error) {
        if (error != null) {
            msg = error.getMsg();
            code = error.getCode();
        }
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
