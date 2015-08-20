package ru.kpfu.itis.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import ru.kpfu.itis.dto.serializer.CustomDoubleSerializer;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Rigen on 12.07.15.
 */
@ApiModel("AccountBadge")
public class AccountBadgeDto {

    private Long id;

    @JsonProperty(value = "account_id")
    private Long accountId;

    @JsonProperty(value = "badge_id")
    private Long badgeId;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private BigDecimal theory = new BigDecimal(0.0).setScale(2);

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private BigDecimal practice = new BigDecimal(0.0).setScale(2);

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private BigDecimal progress = new BigDecimal(0.0).setScale(2);

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public BigDecimal getTheory() {
        return theory;
    }

    public void setTheory(BigDecimal theory) {
        theory = theory.setScale(2);
        this.theory = theory;
    }

    public BigDecimal getPractice() {
        return practice;
    }

    public void setPractice(BigDecimal practice) {
        practice = practice.setScale(2);
        this.practice = practice;
    }

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        progress = practice.setScale(2);
        this.progress = progress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
