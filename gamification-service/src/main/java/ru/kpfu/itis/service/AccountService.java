package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.AccountProfileDto;
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

    List<Account> getStudentsByGroups(Long[] ids);

    Account createAnonymousUser(String login);

    AccountProfileDto getUserProfile(Long id);
}
