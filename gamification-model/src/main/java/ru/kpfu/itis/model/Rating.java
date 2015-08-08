package ru.kpfu.itis.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Rigen on 15.07.15.
 */
@Entity
@Table(name = "RATING")
public class Rating implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "ACCOUNT_INFO_ID")
    private AccountInfo accountInfo;

    @Column(name = "POSITION")
    private Integer position;

    @Column(name = "POINT")
    private Double point = 0.0;

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }
}
