package ru.kpfu.itis.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import ru.kpfu.itis.dto.serializer.CustomDoubleSerializer;
import ru.kpfu.itis.model.enums.BadgeAchievementStatus;

import java.util.Date;


/**
 * Created by Rigen on 12.07.15.
 */
@ApiModel("AccountBadge")
public class AccountBadgeDto {

//    private Long id;
//
//    @JsonProperty(value = "account_id")
//    private Long accountId;
//
//    @JsonProperty(value = "badge_id")
//    private Long badgeId;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private Double theory = 0.0;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private Double practice = 0.0;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private Double progress = 0.0;

    private Date date;

    private BadgeAchievementStatus type = BadgeAchievementStatus.PERFORM;

    public Long getId() {
//        return id;
        return null;
    }

    public void setId(Long id) {
//        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTheory() {
        return theory;
    }

    public void setTheory(Double theory) {
        this.theory = theory;
    }

    public Double getPractice() {
        return practice;
    }

    public void setPractice(Double practice) {
        this.practice = practice;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Long getAccountId() {
//        return accountId;
        return null;
    }

    public void setAccountId(Long accountId) {
//        this.accountId = accountId;
    }

    public void setBadgeId(Long badgeId) {
//        this.badgeId = badgeId;
    }

    public Long getBadgeId() {
//        return badgeId;
        return null;
    }

    public BadgeAchievementStatus getType() {
        return type;
    }

    public void setType(BadgeAchievementStatus type) {
        this.type = type;
    }
}
