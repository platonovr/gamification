package ru.kpfu.itis.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseLongIdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATE_TIME", nullable = false)
    private Date createTime;

    @Version
    @Column(name = "CHANGE_TIME", nullable = false)
    private Date changeTime;

    @Column(name = "FINISH_TIME")
    private Date finishTime;


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BaseLongIdEntity) && (getId() != null) && getId().equals(((BaseLongIdEntity) obj).getId());
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
