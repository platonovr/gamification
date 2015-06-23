package ru.kpfu.itis.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.SimpleDao;
import ru.kpfu.itis.model.IdentifiedEntity;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Repository
public class SimpleDaoImpl extends HibernateDaoSupport implements SimpleDao {

    @Resource(name = "sessionFactory")
    @Override
    public void configure(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public <D> List<D> fetchAll(final Class<D> aClass) {
        return getHibernateTemplate().execute(new HibernateCallback<List<D>>() {
            @Override
            public List<D> doInHibernate(Session aSession) throws HibernateException {
                return aSession.createCriteria(aClass).list();
            }
        });
    }

    @Override
    public <D> D findById(Class<D> aClass, Serializable aId) {
        return getHibernateTemplate().get(aClass, aId);
    }

    @Override
    public <E extends IdentifiedEntity> E loadById(Class<E> aClass, Serializable aId) {
        return getHibernateTemplate().load(aClass, aId);
    }

    @Override
    public <D> D findByField(Class<D> aClass, String aFieldName, Object aValue) {
        return getHibernateTemplate().execute(new HibernateCallback<D>() {
            @Override
            public D doInHibernate(Session aSession) throws HibernateException {
                return (D) aSession.createCriteria(aClass).add(Restrictions.eq(aFieldName, aValue)).uniqueResult();
            }
        });
    }

    @Override
    public <D> List<D> fetchByField(Class<D> aClass, String aFieldName, Object aValue) {
        return getHibernateTemplate().execute(new HibernateCallback<List<D>>() {
            @Override
            public List<D> doInHibernate(Session aSession) throws HibernateException {
                return (List<D>) aSession.createCriteria(aClass).add(Restrictions.eq(aFieldName, aValue)).list();
            }
        });
    }

    @Override
    @Transactional
    public <D extends IdentifiedEntity> Serializable save(D aEntity) {
        return getHibernateTemplate().save(aEntity);
    }

    @Override
    @Transactional
    public <E extends IdentifiedEntity> void saveOrUpdate(E aEntity) {
        getHibernateTemplate().saveOrUpdate(aEntity);
    }

    @Override
    @Transactional
    public <E extends IdentifiedEntity> void update(E aEntity) {
        getHibernateTemplate().update(aEntity);
    }

    @Override
    @Transactional
    public <E extends IdentifiedEntity> void delete(E aEntity) {
        getHibernateTemplate().delete(aEntity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public <E extends IdentifiedEntity> void evict(E aEntity) {
        getSessionFactory().getCurrentSession().evict(aEntity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void flush() {
        getSessionFactory().getCurrentSession().flush();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void clearSession() {
        getSessionFactory().getCurrentSession().clear();
    }

}
