package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ru.kpfu.itis.model.enums.TaskCategoryType;

/**
 * Created by timur on 02.07.15.
 */
@ApiModel("TaskCategory")
public class TaskCategoryDto {

    private Long id;

    @ApiModelProperty(required = true)
    private String name;

    private String caption;

    @ApiModelProperty(required = true)
    private TaskCategoryType type;

    public TaskCategoryDto() {
    }

    public TaskCategoryDto(Long id, String name, TaskCategoryType type, String caption) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.caption = caption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskCategoryType getType() {
        return type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setType(TaskCategoryType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
