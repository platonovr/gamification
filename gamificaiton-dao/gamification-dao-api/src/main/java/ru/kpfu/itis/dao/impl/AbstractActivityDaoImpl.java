package ru.kpfu.itis.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.ActivityDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Activity;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.enums.ActivityType;
import ru.kpfu.itis.model.enums.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rigen on 20.07.15.
 */
@Repository
public abstract class AbstractActivityDaoImpl extends AbstractGenericDao implements ActivityDao {

    @Override
    public List<Activity> getActivityStream(Account account, List<Task> tasks, Long lastActivityId) {
        //TODO get task ids only
        List<Long> ids = new ArrayList<>();
        for (Task task : tasks) {
            ids.add(task.getId());
        }
        return getHibernateTemplate().execute((aSession) -> {
            Criteria criteria = aSession.createCriteria(Activity.class)
                    .add(Restrictions.ne("account", account));
            if (ids.size() > 0) {
                criteria = criteria.add(Restrictions.not(Restrictions.and(Restrictions.ne("entityType", EntityType.TASK),
                        Restrictions.ne("activityType", ActivityType.TASK_NEW),
                        Restrictions.not(Restrictions.in("entityId", ids)))));
            }
            if (lastActivityId != null) {
                criteria = criteria.add(Restrictions.gt("id", lastActivityId));
            }
            criteria = criteria.addOrder(Order.desc("changeTime"));
            return (List<Activity>) criteria
                    .setMaxResults(15)
                    .list();
        });
    }

}
