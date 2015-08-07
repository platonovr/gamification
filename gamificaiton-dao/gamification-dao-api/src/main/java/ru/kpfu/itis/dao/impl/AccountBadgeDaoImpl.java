package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.AccountBadgeDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@Repository
public class AccountBadgeDaoImpl extends AbstractDaoImpl<AccountBadge, Long> implements AccountBadgeDao {
    protected AccountBadgeDaoImpl() {
        super(AccountBadge.class);
    }

    @Override
    public List<AccountBadge> findAllBadgesByAccount(Account account) {
        return getCurrentSession().createCriteria(AccountBadge.class)
                .add(Restrictions.eq("account.id", account.getId()))
                .add(Restrictions.isNull("finishTime")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public AccountBadge findByBadgeAndAccount(Badge badge, Account account) {
        return (AccountBadge) getCurrentSession().createCriteria(AccountBadge.class)
                .add(Restrictions.eq("account.id", account.getId()))
                .add(Restrictions.eq("badge.id", badge.getId()))
                .add(Restrictions.isNull("finishTime")).uniqueResult();
    }
}
