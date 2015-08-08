package ru.kpfu.itis.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import ru.kpfu.itis.dao.TaskCategoryDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.classifier.TaskCategory;

/**
 * Created by timur on 24.06.15.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractTaskCategoryDaoImpl extends AbstractGenericDao implements TaskCategoryDao {
    @Override
    public TaskCategory findByName(String name) {
        return getHibernateTemplate().execute(new HibernateCallback<TaskCategory>() {
            @Override
            public TaskCategory doInHibernate(Session session) throws HibernateException {
                return (TaskCategory) session.createQuery(" from TaskCategory tc " +
                        " where tc.name = :name")
                        .setParameter("name", name)
                        .uniqueResult();
            }
        });
    }

    @Override
    public TaskCategory save(TaskCategory taskCategory) {
        getHibernateTemplate().saveOrUpdate(taskCategory);
        return taskCategory;
    }
}
