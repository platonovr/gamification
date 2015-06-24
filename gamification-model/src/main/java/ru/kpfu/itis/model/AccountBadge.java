package ru.kpfu.itis.model;


import org.hibernate.annotations.CreationTimestamp;
import ru.kpfu.itis.model.enums.TaskStatus;

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
    @Column(nullable = false)
    private Account account;

    @OneToOne
    private Badge badge;

    @Override
    @CreationTimestamp
    public Date getCreateTime() {
        return super.getCreateTime();
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
