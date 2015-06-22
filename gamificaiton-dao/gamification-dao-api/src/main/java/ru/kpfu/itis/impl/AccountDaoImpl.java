package ru.kpfu.itis.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.AccountDao;

/**
 * Created by Rigen on 22.06.15.
 */

@Repository("accountDao")
public class AccountDaoImpl extends SimpleDaoImpl implements AccountDao {
}
