package ru.kpfu.itis.dto;

/**
 * Created by timur on 23.06.15.
 */
public class ResponseDTO<T> {

    private String message;

    private T value;

    private int statusCode;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseDTO(String message, T value, int statusCode) {
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
