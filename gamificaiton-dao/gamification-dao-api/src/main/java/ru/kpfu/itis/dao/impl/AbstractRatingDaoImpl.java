package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import ru.kpfu.itis.dao.RatingDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;
import ru.kpfu.itis.model.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rigen on 17.07.15.
 */

@SuppressWarnings("unchecked")
public abstract class AbstractRatingDaoImpl extends AbstractGenericDao implements RatingDao {

    @Override
    public List<Rating> getRating(Faculty faculty, Integer entranceYear, Integer offset, Integer limit) {
        return getRatingOrderedByField(faculty, entranceYear, offset, limit, "position", true);
    }

    @Override
    public List<Rating> getRatingOrderedByPoint(Faculty faculty, Integer entranceYear, Integer offset, Integer limit) {
        return getRatingOrderedByField(faculty, entranceYear, offset, limit, "point", false);
    }

    @Override
    public List<Rating> getRatingOrderedByField(Faculty faculty, Integer entranceYear, Integer offset,
                                                Integer limit, String orderField, boolean order) {
        return getHibernateTemplate().execute((aSession) -> {
            Criteria criteria = aSession.createCriteria(Rating.class)
                    .createAlias("accountInfo", "account")
                    .createAlias("account.account", "user")
                    .createAlias("account.group", "group")
                    .add(Restrictions.eq("account.entranceYear", entranceYear))
                    .add(Restrictions.eq("account.faculty", faculty))
                    .add(Restrictions.eq("user.role", Role.STUDENT))
                    .add(Restrictions.isNotNull("account.group"));
            if (order) {
                criteria.addOrder(Order.asc(orderField));
            } else {
                criteria.addOrder(Order.desc(orderField));
            }
            if (limit != null)
                criteria.setMaxResults(limit);
            if (offset != null)
                criteria.setFirstResult(offset);
            return (List<Rating>) criteria.list();
        });
    }

    @Override
    public List<Rating> search(Faculty faculty, Integer entranceYear,
                               String searchString, Integer offset, Integer limit) {
        return getHibernateTemplate().execute((aSession) -> {
            Criteria criteria = aSession.createCriteria(Rating.class)
                    .createAlias("accountInfo", "account")
                    .createAlias("account.account", "user")
                    .createAlias("account.group", "group");
            if (entranceYear != null) {
                criteria.add(Restrictions.eq("account.entranceYear", entranceYear));
            }
            if (faculty != null) {
                criteria.add(Restrictions.eq("account.faculty", faculty));
            }

            criteria.add(Restrictions.eq("user.role", Role.STUDENT))
                    .add(Restrictions.isNotNull("account.group"));


            if (searchString != null && !searchString.replaceAll("\\s+", " ").equals(" ")) {
                StringTokenizer stringTokenizer = new StringTokenizer(searchString, " ");
                ArrayList<Criterion> criterions = new ArrayList<>();
                Criterion criterion = null;
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    criterion = Restrictions.or(Restrictions.like("account.firstName", token, MatchMode.ANYWHERE).ignoreCase(),
                            Restrictions.like("account.middleName", token, MatchMode.ANYWHERE).ignoreCase(),
                            Restrictions.like("account.lastName", token, MatchMode.ANYWHERE).ignoreCase(),
                            Restrictions.like("group.name", token, MatchMode.ANYWHERE).ignoreCase());
                    criterions.add(criterion);
                }

                if (criterions.size() > 1) {
                    criterion = Restrictions.and(criterions.get(0), criterions.get(1));
                    for (int i = 2; i < criterions.size(); i++) {
                        criterion = Restrictions.and(criterion, criterions.get(i));
                    }
                }
                if (criterion != null) {
                    criteria.add(criterion);
                }
            }
            criteria.addOrder(Order.asc("position"));
            if (limit != null)
                criteria.setMaxResults(limit);
            if (offset != null)
                criteria.setFirstResult(offset);
            return (List<Rating>) criteria.list();
        });
    }

    @Override
    public Rating getUserRating(Long accountInfoId) {
        return getHibernateTemplate().execute((aSession) ->
                (Rating) aSession.createCriteria(Rating.class)
                        .add(Restrictions.eq("accountInfo.id", accountInfoId)).setMaxResults(1).uniqueResult());
    }

    @Override
    public void update(Rating entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void save(Rating entity) {
        getHibernateTemplate().save(entity);
    }

}

