package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.enums.Role;

import java.util.List;

/**
 * Created by timur on 24.06.15.
 */
public interface AccountDao {

    Account findByLogin(String login);

    List<Account> getAccountsByRole(Role type);

    List<Account> getAccountsByRoleAndGroups(Role type, Long[] ids);
}
