package ru.kpfu.itis.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.impl.AbstractActivityDaoImpl;
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
public class ActivityDaoImpl extends AbstractActivityDaoImpl implements ActivityDao {

    @Override
    public List<Activity> getActivityStream(Account account, List<Task> tasks) {
        List<Long> ids = new ArrayList<>();
        for (Task task : tasks) {
            ids.add(task.getId());
        }
        return getHibernateTemplate().execute((aSession) ->
                (List<Activity>) aSession.createCriteria(Activity.class)
                        .add(Restrictions.ne("account", account))
                        .add(Restrictions.not(Restrictions.and(Restrictions.ne("entityType", EntityType.TASK),
                                Restrictions.ne("activityType", ActivityType.NEW),
                                Restrictions.not(Restrictions.in("entityId", ids))))).list());
    }

}
