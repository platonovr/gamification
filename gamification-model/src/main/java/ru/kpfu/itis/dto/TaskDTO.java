package ru.kpfu.itis.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by timur on 17.06.15.
 */
public class TaskDTO {

    private Long id;

    private String name;

    private String subject;

    private String category;

    private String creator;

    private List<String> coursesOrGroups;

    private List<String> performers;

    private Byte maxMark;

    private Date deadline;

    private String description;

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

    public List<String> getCoursesOrGroups() {
        return coursesOrGroups;
    }

    public void setCoursesOrGroups(List<String> coursesOrGroups) {
        this.coursesOrGroups = coursesOrGroups;
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
}
