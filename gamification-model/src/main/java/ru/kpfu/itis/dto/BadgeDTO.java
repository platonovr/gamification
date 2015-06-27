package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Rigen on 26.06.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadgeDTO {
    private String name;
    private String image;
    private String type;
    private String description;
    private List<TaskDTO> challenges;

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

    public List<TaskDTO> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<TaskDTO> challenges) {
        this.challenges = challenges;
    }
}
