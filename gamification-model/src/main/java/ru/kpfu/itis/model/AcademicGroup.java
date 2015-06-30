package ru.kpfu.itis.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timur on 30.06.15.
 */
@Entity
@Table(name = "ACADEMIC_GROUP")
@AttributeOverride(name = "id", column = @Column(name = "GROUP_ID"))
public class AcademicGroup extends BaseLongIdEntity {

    @Column(name = "GROUP_NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "FORMATION_TIME", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate formationTime;

    @OneToMany(mappedBy = "group")
    private Set<AccountInfo> accountInfos = new HashSet<>();

    @ManyToMany(mappedBy = "academicGroups")
    private Set<Task> tasks = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFormationTime() {
        return formationTime;
    }

    public void setFormationTime(LocalDate formationTime) {
        this.formationTime = formationTime;
    }

    public Set<AccountInfo> getAccountInfos() {
        return accountInfos;
    }

    public void setAccountInfos(Set<AccountInfo> accountInfos) {
        this.accountInfos = accountInfos;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
