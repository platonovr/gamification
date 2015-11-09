package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.AcademicGroup;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.StudyCourse;
import ru.kpfu.itis.model.Subject;
import ru.kpfu.itis.model.enums.Role;

import java.util.List;

/**
 * Created by timur on 24.06.15.
 */
public interface AccountDao {

    Account findByLogin(String login);

    List<Account> getAccountsByRole(Role type, List<Long> groupIds);

    List<Account> getAccountsByRoleAndGroups(Role type, String[] groups);

    Account getAccountWithDependencies(Long id);

    List<Subject> getSubjects(List<Long> subjectIds);

    List<AcademicGroup> getAcademicGroups(List<Long> groupIds);

    List<StudyCourse> getStudyCourses(List<Long> groupIds);
}
