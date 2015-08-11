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

    @Column(name = "ACCOUNT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
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
}
