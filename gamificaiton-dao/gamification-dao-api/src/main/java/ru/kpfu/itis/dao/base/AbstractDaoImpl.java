package ru.kpfu.itis.dao.base;


import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.itis.dao.AbstractDao;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, I> {

    private Class<E> entityClass;
    @Autowired
    private SessionFactory sessionFactory;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        return (List<E>) getCurrentSession().createQuery("select e from " + getEntityName() + " e").list();
    }

    @Override
    public List<E> findAllAndSortBy(String columnName, boolean isAsc) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        if (isAsc) {
            criteria.addOrder(Order.asc(columnName));
        } else {
            criteria.addOrder(Order.desc(columnName));
        }
        return criteria.list();
    }

    @Override
    public List<E> searchFor(String columnName, String term) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass)
                .add(Restrictions.like(columnName, term, MatchMode.ANYWHERE).ignoreCase());
        return criteria.list();
    }

    @Override
    public E findByField(String aFieldName, Object aValue) {
        return (E) getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(aFieldName, aValue)).uniqueResult();
    }

    @Override
    public List<E> fetchByField(String aFieldName, Object aValue) {
        return getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(aFieldName, aValue)).list();
    }

    @Override
    public I save(E entity) {
        I id = (I) getCurrentSession().save(entity);
        return id;
    }

    @Override
    public void update(E newEntity) {
        getCurrentSession().saveOrUpdate(newEntity);
    }

    @Override
    public void edit(E entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public long count() {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        return (long) criteria
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public void delete(E entity) {
        getCurrentSession().delete(entity);

    }

    @Override
    public void delete(I id) {
        Query query = getCurrentSession().createQuery("delete from " + getEntityName() + " e where e.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAll() {
        Query query = getCurrentSession().createQuery("delete from " + getEntityName());
        query.executeUpdate();
    }

    protected String getEntityName() {
        return entityClass.getName();
    }

}
