package ru.kpfu.itis.model;

import ru.kpfu.itis.model.enums.ActivityType;
import ru.kpfu.itis.model.enums.EntityType;

import javax.persistence.*;

/**
 * Created by Rigen on 08.08.15.
 */
@Entity
@Table(name = "ACTIVITY")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ACTIVITY_ID"))
})
public class Activity extends BaseLongIdEntity {

    @Column(name = "ENTITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    EntityType entityType;

    @Column(name = "ACTIVITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    Account account;

    @Column(name = "ENTITY_ID")
    Long entityId;

    public Activity(EntityType entityType, ActivityType activityType, Account account, Long entityId) {
        this.entityType = entityType;
        this.activityType = activityType;
        this.account = account;
        this.entityId = entityId;
    }

    public Activity() {
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
