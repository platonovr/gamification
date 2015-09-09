package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.kpfu.itis.dao.ActivityDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Activity;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.enums.ActivityType;
import ru.kpfu.itis.model.enums.EntityType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rigen on 20.07.15.
 */
public abstract class AbstractActivityDaoImpl extends AbstractGenericDao implements ActivityDao {

    @Override
    public List<Activity> getActivityStream(Account account, List<Task> tasks, Long lastActivityId) {
        //TODO get task ids only
        List<Long> ids = tasks.stream().map(Task::getId).collect(Collectors.toList());
        return getHibernateTemplate().execute((aSession) -> {
            Criteria criteria = aSession.createCriteria(Activity.class)
                    .add(Restrictions.ne("account", account));
            if (ids.size() > 0) {
                criteria = criteria.add(Restrictions.not(Restrictions.and(Restrictions.eq("entityType", EntityType.TASK),
                        Restrictions.eq("activityType", ActivityType.TASK_NEW),
                        Restrictions.not(Restrictions.in("entityId", ids)))));
            }
            if (lastActivityId != null) {
                criteria = criteria.add(Restrictions.lt("id", lastActivityId));
            }
            criteria = criteria.addOrder(Order.desc("id"));
            return (List<Activity>) criteria
                    .setMaxResults(15)
                    .list();
        });
    }

}
