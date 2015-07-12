package ru.kpfu.itis.dto;

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

    private String type;

    private String description;

    private List<TaskDto> challenges;

    public BadgeDto() {
    }

    public BadgeDto(Long id, String name, String image, String type, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.description = description;
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
