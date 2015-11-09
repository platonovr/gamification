package ru.kpfu.itis.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import ru.kpfu.itis.model.enums.Role;
import ru.kpfu.jbl.auth.domain.AuthUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Roman on 22.03.2015.
 */
@Entity
@Table(name = "ACCOUNT")
@AttributeOverride(name = "id", column = @Column(name = "ACCOUNT_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account extends BaseLongIdEntity implements AuthUser {


    @Column(name = "LOGIN", length = 64, nullable = false)
    private String login;

    @Column(name = "PASSWORD", length = 64)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private AccountInfo accountInfo;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<AccountTask> accountTasks = new ArrayList<>();


    /*
        List of subjects,which teacher has got
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ACCOUNT_SUBJECT",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID", nullable = false))
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TEACHER_ACADEMIC_GROUP",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "ACADEMIC_GROUP_ID", nullable = false))
    private Set<AcademicGroup> academicGroups = new HashSet<>();


    @OneToMany(mappedBy = "author")
    private Set<Task> tasks = new HashSet<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUserRole() {
        return role != null ? role.name() : null;
    }

    @Override
    public String getSalt() {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<AccountTask> getAccountTasks() {
        return accountTasks;
    }

    public void setAccountTasks(List<AccountTask> accountTasks) {
        this.accountTasks = accountTasks;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<AcademicGroup> getAcademicGroups() {
        return academicGroups;
    }

    public void setAcademicGroups(Set<AcademicGroup> academicGroups) {
        this.academicGroups = academicGroups;
    }
}
