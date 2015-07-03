package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ru.kpfu.itis.model.enums.TaskCategoryType;

/**
 * Created by timur on 02.07.15.
 */
@ApiModel("TaskCategory")
public class TaskCategoryDto {

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private TaskCategoryType type;

    public TaskCategoryDto() {
    }

    public TaskCategoryDto(String name, TaskCategoryType type) {
        this.name = name;
        this.type = type;
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

    public void setType(TaskCategoryType type) {
        this.type = type;
    }
}
