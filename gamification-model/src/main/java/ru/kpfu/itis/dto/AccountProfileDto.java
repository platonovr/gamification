package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountProfileDto extends ErrorDto {

    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private List<BadgeDto> badges;

    private Double rating;

    private Integer ratingPosition;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingPosition() {
        return ratingPosition;
    }

    public void setRatingPosition(Integer ratingPosition) {
        this.ratingPosition = ratingPosition;
    }
}
