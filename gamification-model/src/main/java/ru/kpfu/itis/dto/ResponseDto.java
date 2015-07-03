package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by timur on 23.06.15.
 */
@ApiModel("Response")
public class ResponseDto<T> {

    @ApiModelProperty(required = true)
    private String message;

    private T value;

    private int statusCode;

    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseDto(String message, T value, int statusCode) {
        this.message = message;
        this.value = value;
        this.statusCode = statusCode;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
