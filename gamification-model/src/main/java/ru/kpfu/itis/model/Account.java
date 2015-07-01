package ru.kpfu.itis.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import ru.kpfu.itis.model.enums.Role;
import ru.kpfu.jbl.auth.domain.AuthUser;
import ru.kpfu.jbl.auth.service.UserServiceAuth;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUserRole() {
        return role != null ? role.name() : null;
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
}
