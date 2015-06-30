package ru.kpfu.itis.dto;

/**
 * Created by timur on 23.06.15.
 */
public class ResponseDto<T> {

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
