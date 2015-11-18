package ru.kpfu.itis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.Rating;
import ru.kpfu.itis.model.SimpleAuthUser;
import ru.kpfu.itis.service.RatingService;
import ru.kpfu.jbl.auth.service.SecurityContextHolderService;

import java.io.Serializable;

import static java.util.Optional.ofNullable;

/**
 * @date 30.04.14
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    SimpleDao simpleDao;

    //loading only by web configs
    @Autowired(required = false)
    SecurityContextHolderService<SimpleAuthUser> securityContextHolderService;

    @Autowired
    private RatingService ratingService;

    @Override
    public Account getCurrentUser() {
        if (securityContextHolderService == null) {
            return null;
        }
        SimpleAuthUser currentUser = securityContextHolderService.getCurrentUser();
        if (currentUser == null) {
            currentUser = (SimpleAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Account account = simpleDao.findById(Account.class, currentUser.getId());
        Serializable accountInfoId = null;
        boolean haveRating = false;
        if (account != null) {
            AccountInfo info = account.getAccountInfo();
            if (info == null) {
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setAccount(account);
                accountInfoId = (Long) simpleDao.save(accountInfo);
            } else {
                Rating existingRating = simpleDao.findById(Rating.class, info.getId());
                haveRating = existingRating != null;
            }
            if (accountInfoId != null) {
                info = simpleDao.findById(AccountInfo.class, accountInfoId);
            }
            if (!haveRating)
                ratingService.createUserRating(info, 0.0);
        }
        return ofNullable(account)
                .orElse(null);
    }

    @Override
    public Long getCurrentUserId() {
        return ofNullable(getCurrentUser()).map(Account::getId).orElse(null);
    }


}
