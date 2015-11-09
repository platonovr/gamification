package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.model.AcademicGroup;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.StudyCourse;
import ru.kpfu.itis.model.Subject;
import ru.kpfu.jbl.auth.service.UserServiceAuth;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
public interface AccountService extends UserServiceAuth {
    Account findById(Long id);

    Account getAccountWithDependencies(Long id);

    List<Account> getTeachers();

    List<Account> getStudents(Long teacherId);

    List<Account> getStudentsByGroups(String[] groups);

    Account createAnonymousUser(String login);

    AccountProfileDto getUserProfile(Long id);

    List<Subject> getSubjects(Account account);

    List<AcademicGroup> getAcademicGroups(Account account);

    List<StudyCourse> getStudyCourses(Account account);
}
