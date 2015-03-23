package ru.kpfu.itis.model;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseLongIdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
