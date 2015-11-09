package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.AccountBadgeDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.AccountBadge;
import ru.kpfu.itis.model.Badge;

import java.util.List;

/**
 * Created by Rigen on 22.06.15.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAccountBadgeDaoImpl extends AbstractGenericDao implements AccountBadgeDao {

    @Override
    public List<AccountBadge> findAllBadgesByAccount(Account account) {
        return getHibernateTemplate().execute((aSession) ->
                (List<AccountBadge>) aSession.createCriteria(AccountBadge.class)
                        .add(Restrictions.eq("account.id", account.getId()))
                        .add(Restrictions.isNull("finishTime")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
    }

    @Override
    public AccountBadge findByBadgeAndAccount(Badge badge, Account account) {
        return getHibernateTemplate().execute((aSession) -> {
            Criteria criteria = aSession.createCriteria(AccountBadge.class)
                    .add(Restrictions.eq("account.id", account.getId()))
                    .add(Restrictions.isNull("finishTime"));
            if (badge != null) {
                criteria = criteria.add(Restrictions.eq("badge.id", badge.getId()));
            }
            return (AccountBadge) criteria.uniqueResult();
        });
    }

    @Override
    public List<Badge> fetchBadgesBySubject(List<Long> subjectIds) {
        return getHibernateTemplate().execute(new HibernateCallback<List<Badge>>() {
            @Override
            public List<Badge> doInHibernate(Session session) throws HibernateException {
                String queryText = " from Badge b ";
                if (subjectIds != null && subjectIds.size() > 0) {
                    queryText += " where b.subject.id in (:subjectIds) ";
                }
                Query query = session.createQuery(queryText);
                if (subjectIds != null && subjectIds.size() > 0) {
                    query.setParameterList("subjectIds", subjectIds);

                }
                return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .setReadOnly(true)
                        .list();
            }
        });
    }
}
