package ru.kpfu.itis.dao.base;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.model.IdentifiedEntity;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @date 20.05.14
 */
public abstract class AbstractGenericDao<T extends IdentifiedEntity> extends HibernateDaoSupport
        implements GenericDao<T> {

    @Resource(name = "sessionFactory")
    protected void configure(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public <Key extends Serializable> T getEntity(Class<T> entityClass, Key entityId) {
        return getHibernateTemplate().get(entityClass, entityId);
    }

    @Override
    @Transactional
    public T save(Class<T> entityClass, T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        return entity;
    }
}
