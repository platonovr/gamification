package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by timur on 17.06.15.
 */
@ApiModel("Challenge")
public class TaskDto {

    private Long id;

    @ApiModelProperty(required = true)
    private String name;

    private String description;

    private SubjectDto subject;

    @JsonProperty("max_count")
    private Integer maxPerformers;

    @ApiModelProperty(allowableValues = "ASSIGNED, INPROGRESS, CANCELED, COMPLETED, NOT_STARTED")
    private String status;

    @ApiModelProperty(required = true)
    private String category;

    private String creator;

    private BadgeDto badge;

    @JsonProperty("current_volume")
    private Byte currentMark;

    @ApiModelProperty(required = true)
    @JsonProperty("max_volume")
    private Byte maxMark;

    @ApiModelProperty(required = true)
    @JsonProperty("date_from")
    private Date startDate;

    @ApiModelProperty(required = true)
    @JsonProperty("date_to")
    private Date deadline;

    @ApiModelProperty(required = true)
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(required = true)
    @JsonProperty("update_time")
    private Date changeTime;

    private List<String> labels = new LinkedList<>();

    public TaskDto() {
    }

    public TaskDto(Long id, String name, String description, SubjectDto subject,
                   Integer maxPerformers, String status, String category, String creator,
                   BadgeDto badge, Byte currentMark, Byte maxMark, Date startDate, Date deadline,
                   Date createTime, Date changeTime, List<String> labels) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.maxPerformers = maxPerformers;
        this.status = status;
        this.category = category;
        this.creator = creator;
        this.badge = badge;
        this.currentMark = currentMark;
        this.maxMark = maxMark;
        this.startDate = startDate;
        this.deadline = deadline;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.labels = labels;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Byte getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Byte maxMark) {
        this.maxMark = maxMark;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getMaxPerformers() {
        return maxPerformers;
    }

    public void setMaxPerformers(Integer maxPerformers) {
        this.maxPerformers = maxPerformers;
    }

    public BadgeDto getBadge() {
        return badge;
    }

    public void setBadge(BadgeDto badge) {
        this.badge = badge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Byte getCurrentMark() {
        return currentMark;
    }

    public void setCurrentMark(Byte currentMark) {
        this.currentMark = currentMark;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }
}
