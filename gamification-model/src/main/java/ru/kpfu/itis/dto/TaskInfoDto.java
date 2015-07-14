package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timur on 13.07.15.
 */
@ApiModel("TaskInfo")
public class TaskInfoDto extends TaskDto {

    private List<String> groups = new ArrayList<>();
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
