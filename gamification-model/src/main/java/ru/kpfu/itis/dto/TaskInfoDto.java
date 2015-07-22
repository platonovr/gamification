package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timur on 13.07.15.
 */
@ApiModel("TaskInfo")
public class TaskInfoDto extends TaskDto {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> groups = new ArrayList<>();

    @JsonProperty("users")
    private List<AccountInfoDto> performers = new ArrayList<>();

    public TaskInfoDto() {
    }

    public TaskInfoDto(TaskDto taskDto) {
        super(taskDto.getId(), taskDto.getName(), taskDto.getDescription(),
                taskDto.getSubject(), taskDto.getMaxPerformers(), taskDto.getStatus(),
                taskDto.getCategory(), taskDto.getCreator(), taskDto.getBadge(),
                taskDto.getCurrentMark(), taskDto.getMaxMark(),
                taskDto.getStartDate(), taskDto.getDeadline(), taskDto.getCreateTime(), taskDto.getChangeTime(),
                taskDto.getLabels());
    }

    public List<AccountInfoDto> getPerformers() {
        return performers;
    }

    public void setPerformers(List<AccountInfoDto> performers) {
        this.performers = performers;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
