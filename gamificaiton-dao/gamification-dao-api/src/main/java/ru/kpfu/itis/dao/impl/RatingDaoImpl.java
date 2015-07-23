package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.RatingDao;
import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;

import java.util.List;

/**
 * Created by Rigen on 17.07.15.
 */

@Repository
public class RatingDaoImpl extends SimpleDaoImpl implements RatingDao {

    @Override
    public List<Rating> getRating(Faculty faculty, Integer entranceYear, Double offset, Integer limit) {
        return getRatingOrderedByField(faculty, entranceYear, offset, limit, "position", true);
    }

    @Override
    public List<Rating> getRatingOrderedByPoint(Faculty faculty, Integer entranceYear, Double offset, Integer limit) {
        return getRatingOrderedByField(faculty, entranceYear, offset, limit, "point", false);
    }

    @Override
    public List<Rating> getRatingOrderedByField(Faculty faculty, Integer entranceYear, Double offset,
                                                Integer limit, String orderField, boolean order) {
        return getHibernateTemplate().execute((aSession) -> {
            Criteria criteria = aSession.createCriteria(Rating.class)
                    .createAlias("accountInfo", "account")
                    .add(Restrictions.eq("account.entranceYear", entranceYear))
//                    .createAlias("account.faculty", "faculty")
                    .add(Restrictions.eq("account.faculty", faculty));
            if (offset != null)
                criteria.add(Restrictions.le("point", offset));
            if (order) {
                criteria.addOrder(Order.asc(orderField));
            } else {
                criteria.addOrder(Order.desc(orderField));
            }
            if (limit != null)
                criteria.setMaxResults(limit);
            return (List<Rating>) criteria.list();
        });
    }

    @Override
    public Rating getUserRating(Long accountInfoId) {
        return getHibernateTemplate().execute((aSession) ->
                (Rating) aSession.createCriteria(Rating.class)
                        .add(Restrictions.eq("accountInfo.id", accountInfoId)).uniqueResult());
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

