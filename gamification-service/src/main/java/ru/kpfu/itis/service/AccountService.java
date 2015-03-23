package ru.kpfu.itis.service;


import ru.kpfu.itis.model.Account;

/**
 * Created by Дамир on 06.02.2015.
 */

public interface AccountService {

    public Account findUserByLogin(String login);

}
