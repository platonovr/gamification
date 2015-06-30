package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Rigen on 26.06.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadgeDto {
    private Long id;
    private String name;
    private String image;
    private String type;
    private String description;
    private List<TaskDto> challenges;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
