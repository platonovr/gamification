package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;

/**
 * Created by Rigen on 26.06.15.
 */
@ApiModel("Account")
public class AccountInfoDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String photo;
    private Double rating;
    private String group;

    public AccountInfoDto() {
    }

    public AccountInfoDto(Long id, String first_name, String last_name, String group) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
