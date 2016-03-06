package ru.kpfu.itis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.SimpleAuthUser;
import ru.kpfu.jbl.auth.service.SecurityContextHolderService;

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

    @Override
    public Account getCurrentUser() {
        if (securityContextHolderService == null) {
            return null;
        }
        SimpleAuthUser currentUser = securityContextHolderService.getCurrentUser();
        return ofNullable(simpleDao.findById(Account.class, currentUser.getId()))
                .orElse(null);
    }

    @Override
    public Long getCurrentUserId() {
        return ofNullable(getCurrentUser()).map(Account::getId).orElse(null);
    }


}
