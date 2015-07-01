package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountProfileDto {
    private Long id;
    private String login;
    private String first_name;
    private String last_name;
    private List<BadgeDto> badges;
    private Double rating;
    private Integer rating_position;

    public List<BadgeDto> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeDto> badges) {
        this.badges = badges;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRating_position() {
        return rating_position;
    }

    public void setRating_position(Integer rating_position) {
        this.rating_position = rating_position;
    }
}
