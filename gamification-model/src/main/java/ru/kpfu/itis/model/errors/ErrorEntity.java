package ru.kpfu.itis.model.errors;

import java.util.List;

/**
 * Created by Владислав on 14.01.2015.
 */
public class ErrorEntity {
    private String code;
    private String message;
    private List<CustomErrorFields> customErrorFields;

    public ErrorEntity() {
    }


    public ErrorEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustomErrorFields> getCustomErrorFields() {
        return customErrorFields;
    }

    public void setCustomErrorFields(List<CustomErrorFields> customErrorFields) {
        this.customErrorFields = customErrorFields;
    }
}
