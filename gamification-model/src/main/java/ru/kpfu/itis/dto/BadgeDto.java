package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by Rigen on 26.06.15.
 */
@ApiModel("Badge")
public class BadgeDto {

    private Long id;

    private String name;

    private String image;

    private SubjectDto subject;

    private String category;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TaskDto> challenges;

    private AccountBadgeDto status;

    public BadgeDto() {
    }

    public BadgeDto(Long id, String name, String image, String category, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.description = description;
    }

    public BadgeDto(Long id, String name, String image, SubjectDto subject, String category, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.subject = subject;
        this.category = category;
        this.description = description;
    }

    public AccountBadgeDto getStatus() {
        return status;
    }

    public void setStatus(AccountBadgeDto status) {
        this.status = status;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskDto> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<TaskDto> challenges) {
        this.challenges = challenges;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }
}
