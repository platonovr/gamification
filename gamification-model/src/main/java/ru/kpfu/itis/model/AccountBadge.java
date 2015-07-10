package ru.kpfu.itis.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by Rigen on 25.06.15.
 */
@Entity
@Table(name = "ACCOUNT_BADGE")
@AttributeOverride(name = "id", column = @Column(name = "ACCOUNT_BADGE_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AccountBadge extends BaseLongIdEntity {

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "BADGE_ID", nullable = false)
    private Badge badge;

    @Column(name = "THEORY")
    private Integer theory = 0;

    @Column(name = "PRACTICE")
    private Integer practice = 0;

    @Column(name = "PROGRESS")
    private Double progress = 0.0;

    @Column(name = "MAX_MARK")
    private Integer maxMark = 0;


    @Override
    @CreationTimestamp
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    public Integer computeMaxMark() {
        switch (badge.getType()) {
            case COMMON:
                return Badge.MAX_STUDY_MARK;
            case SPECIAL:
                return badge.getTasks().size();
            default:
                return 0;
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
}
