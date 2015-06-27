package ru.kpfu.itis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.security.SecurityDao;
import ru.kpfu.itis.model.Account;

/**
 * @date 30.04.14
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityDao securityDao;


    @Override
    @Transactional(readOnly = true)
    public Account getAccount(Long accountId) {
        return securityDao.getAccount(accountId);
    }


    @Override
    @Transactional
    public <T extends Account> T saveAccount(Class<T> accountClass, T account) {
        return securityDao.saveAccount(accountClass, account);
    }


}
