package ru.kpfu.itis.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Roman on 01.09.2015.
 */

public class TaskEditorDto {

    private Long id;

    private String name;

    private SubjectDto subject;

    private AccountDto creator;

    private Integer maxMark;

    private Date date_from;

    private Date date_to;

    private ClassifierDto category;

    private List<CourseOrGroupDto> coursesAndGroups = new ArrayList<>();

    private String description;

    private List<AccountDto> performers = new ArrayList<>();

    private BadgeDto badge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public AccountDto getCreator() {
        return creator;
    }

    public void setCreator(AccountDto creator) {
        this.creator = creator;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public ClassifierDto getCategory() {
        return category;
    }

    public void setCategory(ClassifierDto category) {
        this.category = category;
    }

    public List<CourseOrGroupDto> getCoursesAndGroups() {
        return coursesAndGroups;
    }

    public void setCoursesAndGroups(List<CourseOrGroupDto> coursesAndGroups) {
        this.coursesAndGroups = coursesAndGroups;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AccountDto> getPerformers() {
        return performers;
    }

    public void setPerformers(List<AccountDto> performers) {
        this.performers = performers;
    }

    public Integer getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Integer maxMark) {
        this.maxMark = maxMark;
    }

    public BadgeDto getBadge() {
        return badge;
    }

    public void setBadge(BadgeDto badge) {
        this.badge = badge;
    }
}
