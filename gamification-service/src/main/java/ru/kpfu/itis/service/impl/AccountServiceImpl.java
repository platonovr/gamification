package ru.kpfu.itis.service.impl;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AcademicGroupDao;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.mapper.AccountProfileMapper;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.enums.Role;
import ru.kpfu.itis.processing.badges.AbstractBadgeChecker;
import ru.kpfu.itis.processing.badges.BadgesListBuilder;
import ru.kpfu.itis.processing.badges.BadgesPack;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.*;
import ru.kpfu.jbl.auth.domain.AuthUser;
import ru.kpfu.jbl.auth.provider.encoders.PasswordEncoder;
import ru.kpfu.jbl.auth.response.UserResponse;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static ru.kpfu.itis.BadgeConstants.AUTH_BADGE_ID;

/**
 * Created by Rigen on 22.06.15.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SimpleDao simpleDao;

    @Autowired
    AcademicGroupDao academicGroupDao;

    //only with web module
    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private AccountBadgeService accountBadgeService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AccountProfileMapper accountProfileMapper;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BadgesPack badgesPack;

    @Transactional
    @Override
    public Account findById(Long id) {
        return simpleDao.findById(Account.class, id);
    }

    @Override
    @Transactional
    public Account getAccountWithDependencies(Long id) {
        return accountDao.getAccountWithDependencies(id);
    }

    @Override
    @Transactional
    public List<Account> getTeachers() {
        return accountDao.getAccountsByRole(Role.TEACHER, null);
    }

    @Override
    @Transactional
    public List<Account> getStudents(Long teacherId) {
        Account account = accountDao.getAccountWithDependencies(teacherId);
        List<Long> groupIds = account.getAcademicGroups().stream().map(AcademicGroup::getId).collect(Collectors.toList());
        return accountDao.getAccountsByRole(Role.STUDENT, groupIds);
    }

    @Override
    @Transactional
    public List<Account> getStudentsByGroups(String[] groups) {
        return accountDao.getAccountsByRoleAndGroups(Role.STUDENT, groups);
    }

    @Override
    @Transactional
    public AuthUser findUserByLogin(String s) {
        Account account = accountDao.findByLogin(s);
        if (account == null) {
            if (s != null && s.startsWith("anonymous")) {
                account = createAnonymousUser(s);
                simpleDao.save(account);
                simpleDao.save(account.getAccountInfo());
            }
        }
        if (account == null) {
            return null;
        }
        return new SimpleAuthUser(account);
    }

    /**
     * Created anonymous user with login pattern anonymousGROUP_ID
     *
     * @param login
     * @return
     */
    @Override
    public Account createAnonymousUser(String login) {
        Long academicGroupId;
        try {
            academicGroupId = Long.valueOf(login.replaceAll("[\\D]", ""));
        } catch (Exception e) {
            return null;
        }
        AcademicGroup academicGroup = simpleDao.findById(AcademicGroup.class, academicGroupId);
        if (academicGroup == null) {
            return null;
        }

        Account account = new Account();
        account.setLogin(login);
        if (passwordEncoder != null) {
            account.setPassword(passwordEncoder.encode("password", ""));
            //TODO set salt
        } else {
            account.setPassword("");
        }
        account.setRole(Role.ANONYMOUS);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setGroup(academicGroup);
        accountInfo.setAccount(account);
        account.setAccountInfo(accountInfo);
        return account;
    }

    @Override
    @Transactional
    public AccountProfileDto getUserProfile(Long id) {
        Account account = findById(id);
        if (account == null) {
            return null;
        }
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
        if (accountInfo == null) {
            return null;
        }
        List<AccountBadge> badges = accountBadgeService.findAllBadgesByAccount(account);
        Rating rating = ratingService.getUserRating(accountInfo.getId());
        if (rating == null) {
            ratingService.createUserRating(accountInfo, 0.0);
            ratingService.recalculateRating(accountInfo);
            rating = ratingService.getUserRating(accountInfo.getId());
        }
        return accountProfileMapper.map(account, accountInfo, badges, rating);
    }

    @Override
    @Transactional
    public List<Subject> getSubjects(Account account) {
        account = accountDao.getAccountWithDependencies(account.getId());
        List<Long> subjectIds = account.getSubjects().stream().map(Subject::getId).collect(Collectors.toList());
        return accountDao.getSubjects(subjectIds);
    }

    @Override
    @Transactional
    public List<AcademicGroup> getAcademicGroups(Account account) {
        account = accountDao.getAccountWithDependencies(account.getId());
        List<Long> groupIds = account.getAcademicGroups().stream().map(AcademicGroup::getId).collect(Collectors.toList());
        return accountDao.getAcademicGroups(groupIds);
    }

    @Override
    @Transactional
    public List<StudyCourse> getStudyCourses(Account account) {
        account = accountDao.getAccountWithDependencies(account.getId());
        List<Long> groupIds = account.getAcademicGroups().stream().map(AcademicGroup::getId).collect(Collectors.toList());
        return accountDao.getStudyCourses(groupIds);
    }

    @Override
    public AuthUser saveUser(UserResponse userResponse) {
        if (userResponse.getId() == null) {
            return null;
        }
        Account account = ofNullable(simpleDao.findById(Account.class, userResponse.getId())).orElse(accountDao.findByLogin(userResponse.getLogin()));
        if (account == null) {
            account = new Account();
            account.setLogin(userResponse.getLogin());
            account.setRole(Role.valueOf(userResponse.getRole()));
            account.setPassword("");
            AccountInfo accountInfo = new AccountInfo();
            AcademicGroup academicGroup = academicGroupDao.findByGroupNumber(userResponse.getAcademicGroupName());
            if (academicGroup != null) {
                accountInfo.setGroup(academicGroup);
            } else {
                academicGroup = new AcademicGroup();
                academicGroup.setFormationTime(new LocalDate());
                academicGroup.setName(userResponse.getAcademicGroupName());
                simpleDao.save(academicGroup);
                accountInfo.setGroup(academicGroup);
            }
            if (userResponse.getEntranceYear() != null) {
                accountInfo.setEntranceYear(userResponse.getEntranceYear().intValue());
            }
            String name = userResponse.getName();
            String[] fio = name.split(" ");
            accountInfo.setFirstName(fio[0]);
            if (fio.length > 1) {
                accountInfo.setLastName(fio[1]);
            }
            if (fio.length > 2) {
                accountInfo.setMiddleName(fio[2]);
            }
            Serializable id = simpleDao.save(account);
            account = simpleDao.findById(Account.class, id);
            accountInfo.setAccount(account);
            Long accountInfoId = (Long) simpleDao.save(accountInfo);
            if (accountInfoId != null) {
                accountInfo = simpleDao.findById(AccountInfo.class, accountInfoId);
            }
            ratingService.createUserRating(accountInfo, 0.0);
        }
        return new SimpleAuthUser(account);
    }

    @Override
    public void afterLoginSuccess(AuthUser authUser) {
        List<AbstractBadgeChecker> badgeCheckers = new BadgesListBuilder(badgesPack)
                .get(AUTH_BADGE_ID)
                .build();
        accountBadgeService.applyBadges(badgeCheckers, authUser);
    }
}
