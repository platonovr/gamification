package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.model.Account;

/**
 * Created by Дамир on 06.02.2015.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("from Account where login=?1")
    public Account findUserByLogin(String login);

}
