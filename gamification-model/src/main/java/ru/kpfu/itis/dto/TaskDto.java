package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;
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

    private String subject;

    private Integer maxPerformers;

    @ApiModelProperty(required = true)
    private String category;

    private String creator;

    private List<String> groups;

    private List<String> performers;

    @ApiModelProperty(required = true)
    private Byte maxMark;

    @ApiModelProperty(required = true)
    private Date startDate;

    @ApiModelProperty(required = true)
    private Date deadline;

    private Date createTime;

    private Date changeTime;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getPerformers() {
        return performers;
    }

    public void setPerformers(List<String> performers) {
        this.performers = performers;
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
}
