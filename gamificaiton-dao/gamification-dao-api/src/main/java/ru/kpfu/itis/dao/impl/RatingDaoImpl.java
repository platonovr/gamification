package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.RatingDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;
import ru.kpfu.itis.model.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rigen on 17.07.15.
 */

@Repository
public class RatingDaoImpl extends AbstractDaoImpl<Rating, Long> implements RatingDao {

    protected RatingDaoImpl() {
        super(Rating.class);
    }

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
        Criteria criteria = getCurrentSession().createCriteria(Rating.class)
                .createAlias("accountInfo", "account")
                .createAlias("account.account", "user")
                .createAlias("account.group", "group")
                .add(Restrictions.eq("account.entranceYear", entranceYear))
                .add(Restrictions.eq("account.faculty", faculty))
                .add(Restrictions.eq("user.role", "STUDENT"))
                .add(Restrictions.isNotNull("account.group"));
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
    }

    @Override
    public List<Rating> search(Faculty faculty, Integer entranceYear,
                               String searchString, Double offset, Integer limit) {
        Criteria criteria = getCurrentSession().createCriteria(Rating.class)
                .createAlias("accountInfo", "account")
                .createAlias("account.account", "user")
                .createAlias("account.group", "group")
                .add(Restrictions.eq("account.entranceYear", entranceYear))
                .add(Restrictions.eq("account.faculty", faculty))
                .add(Restrictions.eq("user.role", Role.STUDENT))
                .add(Restrictions.isNotNull("account.group"));
        if (offset != null)
            criteria.add(Restrictions.le("point", offset));

        if (searchString != null && !searchString.replaceAll("\\s+", " ").equals(" ")) {
            StringTokenizer stringTokenizer = new StringTokenizer(searchString, " ");
            ArrayList<Criterion> criterions = new ArrayList<>();
            Criterion criterion = null;
            while (stringTokenizer.hasMoreTokens()) {
                String token = stringTokenizer.nextToken();
                criterion = Restrictions.or(Restrictions.like("account.firstName", token, MatchMode.ANYWHERE),
                        Restrictions.like("account.middleName", token, MatchMode.ANYWHERE),
                        Restrictions.like("account.lastName", token, MatchMode.ANYWHERE),
                        Restrictions.like("group.name", token, MatchMode.ANYWHERE));
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
        return (List<Rating>) criteria.list();
    }

    @Override
    public Rating getUserRating(Long accountInfoId) {
        return (Rating) getCurrentSession().createCriteria(Rating.class)
                .add(Restrictions.eq("accountInfo.id", accountInfoId)).uniqueResult();
    }
}

