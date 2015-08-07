package ru.kpfu.itis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.security.SecurityDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.jbl.auth.service.SecurityContextHolderService;

import static java.util.Optional.ofNullable;

/**
 * @date 30.04.14
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityDao securityDao;

    //loading only by web configs
    @Autowired(required = false)
    SecurityContextHolderService<Account> securityContextHolderService;


    @Override
    @Transactional
    public Account getAccount(Long accountId) {
        return securityDao.getAccount(accountId);
    }


    @Override
    @Transactional
    public <T extends Account> T saveAccount(Class<T> accountClass, T account) {
        return securityDao.saveAccount(accountClass, account);
    }

    @Override
    public Account getCurrentUser() {
        if (securityContextHolderService == null) {
            return null;
        }
        return ofNullable(securityContextHolderService.getCurrentUser())
                .orElse(null);
    }

    @Override
    public Long getCurrentUserId() {
        return ofNullable(getCurrentUser()).map(Account::getId).orElse(null);
    }


}
