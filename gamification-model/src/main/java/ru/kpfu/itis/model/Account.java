package ru.kpfu.itis.model;

import ru.kpfu.itis.model.enums.Role;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Roman on 22.03.2015.
 */
@Entity
@Table(name = "ACCOUNT")
@AttributeOverride(name = "id", column = @Column(name = "ACCOUNT_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account extends BaseLongIdEntity {

    private String login;

    private String password;

    //TODO entity, which contains role and information about it
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private AccountInfo accountInfo;


    @Column(name = "CREATE_TIME", nullable = false)
    private Date createTime;

    @Version
    @Column(name = "CHANGE_TIME", nullable = false)
    private Date changeTime;

    @Column(name = "FINISH_TIME")
    private Date finishTime;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    protected void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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
}
