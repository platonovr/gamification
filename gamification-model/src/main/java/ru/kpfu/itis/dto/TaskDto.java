package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Rigen on 17.06.15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {

    private Integer id;
    private String name;
    private String type;
    private String category;
    private int point;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
