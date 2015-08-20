package ru.kpfu.itis.service;

import ru.kpfu.itis.model.Account;
import ru.kpfu.jbl.auth.service.UserServiceAuth;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountService extends UserServiceAuth {
    Account findById(Long id);

    List<Account> getTeachers();

    List<Account> getStudents();

}
