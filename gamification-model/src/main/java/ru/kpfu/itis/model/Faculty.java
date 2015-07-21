package ru.kpfu.itis.model;

import javax.persistence.*;

/**
 * Created by Rigen on 19.07.15.
 */

@Entity
@Table(name = "FACULTY")
@AttributeOverride(name = "id", column = @Column(name = "FACULTY_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Faculty extends BaseLongIdEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Override
    public Long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
