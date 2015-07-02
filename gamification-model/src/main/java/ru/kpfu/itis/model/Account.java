package ru.kpfu.itis.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import ru.kpfu.itis.model.enums.Role;

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
public class Account extends BaseLongIdEntity {


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

    @OneToMany(mappedBy = "author")
    private Set<Task> tasks = new HashSet<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
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
}
