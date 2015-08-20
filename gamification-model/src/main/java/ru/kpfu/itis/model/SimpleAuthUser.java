package ru.kpfu.itis.model;

import ru.kpfu.jbl.auth.domain.AuthUser;

/**
 * Created by ainurminibaev on 20.08.15.
 */
public class SimpleAuthUser implements AuthUser {
    private Long id;
    private String password;
    private String userRole;
    private String salt;

    public SimpleAuthUser(Account account) {
        if (account != null) {
            this.password = account.getPassword();
            this.userRole = account.getUserRole();
            this.salt = account.getSalt();
            this.id = account.getId();
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
