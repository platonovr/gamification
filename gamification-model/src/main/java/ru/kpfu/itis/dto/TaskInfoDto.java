package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;

import java.util.*;

/**
 * Created by timur on 13.07.15.
 */
@ApiModel("TaskInfo")
public class TaskInfoDto extends TaskDto {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> groups = new ArrayList<>();

    @JsonProperty("users")
    private List<AccountInfoDto> performers = new ArrayList<>();

    @JsonProperty("status_map")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<Long, StatusMark> statusMap;

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

    public Map<Long, StatusMark> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Long, StatusMark> statusMap) {
        this.statusMap = statusMap;
    }

    public static class StatusMark {
        private String status;
        private Integer mark;

        public StatusMark(String status, Integer mark) {
            this.status = status;
            this.mark = mark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getMark() {
            return mark;
        }

        public void setMark(Integer mark) {
            this.mark = mark;
        }
    }
}
