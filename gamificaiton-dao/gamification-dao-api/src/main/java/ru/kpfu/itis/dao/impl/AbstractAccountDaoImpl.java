package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.AccountDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.enums.Role;

import java.util.List;

/**
 * Created by timur on 24.06.15.
 */

@SuppressWarnings("unchecked")
public abstract class AbstractAccountDaoImpl extends AbstractGenericDao implements AccountDao {

    @Override
    public Account findByLogin(String login) {
        return getHibernateTemplate().execute(session -> (Account) session.createQuery("from Account a" +
                " where a.login = :login")
                .setParameter("login", login)
                .uniqueResult());
    }

    @Override
    public List<Account> getAccountsByRole(Role type, List<Long> groupIds) {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<Account>>() {
                    @Override
                    public List<Account> doInHibernate(Session session) throws HibernateException {
                        String query = " from Account acc " +
                                " join  fetch acc.accountInfo ai " +
                                " left join  fetch ai.group g " +
                                " left join  fetch g.course " +
                                " where  acc.role = :neededType ";
                        if (groupIds != null && groupIds.size() > 0) {
                            query += " and g.id in (:groupIds) ";
                        }
                        Query result = session.createQuery(
                                query
                        ).setParameter("neededType", type);
                        if (groupIds != null && groupIds.size() > 0) {
                            result.setParameterList("groupIds", groupIds);
                        }
                        return result.setReadOnly(true)
                                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                                .list();
                    }
                }
        );
    }

    @Override
    public List<Account> getAccountsByRoleAndGroups(Role type, String[] groups) {
        return getHibernateTemplate().execute((aSession) ->
                (List<Account>) aSession.createCriteria(Account.class)
                        .add(Restrictions.eq("role", type))
                        .createAlias("accountInfo", "info")
                        .createAlias("info.group", "group")
                        .add(Restrictions.in("group.name", groups))
                        .add(Restrictions.isNull("finishTime")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
    }

    @Override
    public Account getAccountWithDependencies(Long id) {
        return getHibernateTemplate().execute(new HibernateCallback<Account>() {
            @Override
            public Account doInHibernate(Session session) throws HibernateException {
                return (Account) session.createQuery("from Account a left join fetch a.subjects left join fetch a.academicGroups " +
                        "where a.id = :accountId")
                        .setParameter("accountId", id)
                        .setReadOnly(true)
                        .uniqueResult();
            }
        });
    }

    @Override
    public List<Subject> getSubjects(List<Long> ids) {
        return getHibernateTemplate().execute(new HibernateCallback<List<Subject>>() {
            @Override
            public List<Subject> doInHibernate(Session session) throws HibernateException {
                String queryText = " from Subject s ";
                if (ids != null && ids.size() > 0) {
                    queryText += " where s.id in (:ids) ";
                }
                Query query = session.createQuery(queryText);

                if (ids != null && ids.size() > 0) {
                    query.setParameterList("ids", ids);
                }
                return query
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .setReadOnly(true)
                        .list();
            }
        });
    }

    @Override
    public List<AcademicGroup> getAcademicGroups(List<Long> ids) {
        return getHibernateTemplate().execute(new HibernateCallback<List<AcademicGroup>>() {
            @Override
            public List<AcademicGroup> doInHibernate(Session session) throws HibernateException {
                String hqlQuery = " from AcademicGroup ac ";
                if (ids != null && ids.size() > 0) {
                    hqlQuery += " where ac.id in (:ids) ";
                }
                Query query = session.createQuery(hqlQuery);
                if (ids != null && ids.size() > 0) {
                    query.setParameterList("ids", ids);
                }
                return query
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .setReadOnly(true)
                        .list();
            }
        });
    }

    @Override
    public List<StudyCourse> getStudyCourses(List<Long> ids) {
        return getHibernateTemplate().execute(new HibernateCallback<List<StudyCourse>>() {
            @Override
            public List<StudyCourse> doInHibernate(Session session) throws HibernateException {
                String hqlQuery = " from StudyCourse sc inner join sc.academicGroups ac ";
                if (ids != null && ids.size() > 0) {
                    hqlQuery += " where ac.id in (:ids) ";
                }
                Query query = session.createQuery(hqlQuery);
                if (ids != null && ids.size() > 0) {
                    query.setParameterList("ids", ids);
                }
                return query
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .setReadOnly(true)
                        .list();
            }
        });
    }
}
