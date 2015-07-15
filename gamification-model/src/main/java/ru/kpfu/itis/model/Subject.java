package ru.kpfu.itis.model;

import ru.kpfu.itis.model.classifier.SubjectType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timur on 14.07.15.
 */
@Entity
@Table(name = "SUBJECT")
@AttributeOverride(name = "id", column = @Column(name = "SUBJECT_ID"))
public class Subject extends BaseLongIdEntity {

    @Column(name = "NAME", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_TYPE_ID", nullable = false)
    private SubjectType subjectType;

    @OneToMany(mappedBy = "subject")
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "subject")
    private Set<Badge> badges = new HashSet<>();

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
