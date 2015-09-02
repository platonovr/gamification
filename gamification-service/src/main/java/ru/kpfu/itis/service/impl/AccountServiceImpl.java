package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.mapper.AccountProfileMapper;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.enums.Role;
import ru.kpfu.itis.service.*;
import ru.kpfu.jbl.auth.domain.AuthUser;
import ru.kpfu.jbl.auth.provider.encoders.PasswordEncoder;
import ru.kpfu.jbl.auth.response.UserResponse;

import java.io.Serializable;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Created by Rigen on 22.06.15.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SimpleDao simpleDao;

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

    @Transactional
    @Override
    public Account findById(Long id) {
        return simpleDao.findById(Account.class, id);
    }

    @Override
    @Transactional
    public List<Account> getTeachers() {
        return accountDao.getAccountsByRole(Role.TEACHER);
    }

    @Override
    @Transactional
    public List<Account> getStudents() {
        return accountDao.getAccountsByRole(Role.STUDENT);
    }

    @Override
    public AuthUser findUserByLogin(String s) {
        Account account = accountDao.findByLogin(s);
        if (account == null) {
            if (s != null && s.startsWith("anonymous")) {
                account = createAnonymousUser(s);
                simpleDao.save(account);
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
    public AuthUser saveUser(UserResponse userResponse) {
        if (userResponse.getId() == null) {
            return null;
        }
        Account account = ofNullable(simpleDao.findById(Account.class, userResponse.getId())).orElse(accountDao.findByLogin(userResponse.getLogin()));
        if (account == null) {
            //TODO proper save user
            account = new Account();
            account.setLogin(userResponse.getLogin());
            account.setRole(Role.valueOf(userResponse.getRole()));
            account.setPassword("");
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setFirstName(userResponse.getName());
            Serializable id = simpleDao.save(account);
            account = simpleDao.findById(Account.class, id);
        }
        return account;
    }
}
