package ru.kpfu.itis.model;


import ru.kpfu.itis.model.enums.BadgeCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rigen on 20.06.15.
 */

@Entity
@Table(name = "BADGE")
@AttributeOverride(name = "id", column = @Column(name = "BADGE_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Badge extends BaseLongIdEntity {

    @Transient
    public static final Integer MAX_STUDY_MARK = 100;

    @Enumerated(EnumType.STRING)
    private BadgeCategory type;

    private String description;

    private String name;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @OneToMany(mappedBy = "badge")
    private List<Task> tasks;

    @Column(name = "MAX_MARK")
    private Integer maxMark = 0;

    public Integer computeMaxMark() {
        switch (type) {
            case COMMON:
                return Badge.MAX_STUDY_MARK;
            case SPECIAL:
                return tasks.size();
            default:
                return 0;
        }
    }

    public Integer getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Integer maxMark) {
        this.maxMark = maxMark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BadgeCategory getType() {
        return type;
    }

    public void setType(BadgeCategory type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
