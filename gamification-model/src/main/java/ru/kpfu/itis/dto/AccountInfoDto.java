package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Rigen on 26.06.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInfoDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String photo;
    private Double rating;

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
}
