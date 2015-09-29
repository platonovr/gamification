package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.kpfu.itis.dto.enums.Responses;

/**
 * Created by timur on 10.07.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private String msg;

    private Integer code;

    public ResponseDto(Responses error) {
        if (error != null) {
            msg = error.getMsg();
            code = error.getCode();
        }
    }

    public ResponseDto() {
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isError() {
        return msg != null || code != null;
    }
}
