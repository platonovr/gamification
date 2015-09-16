package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.dao.AcademicGroupDao;
import ru.kpfu.itis.dao.base.AbstractGenericDao;
import ru.kpfu.itis.model.AcademicGroup;

/**
 * Created by Rigen on 22.06.15.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAcademicGroupDaoImpl extends AbstractGenericDao implements AcademicGroupDao {

    @Override
    public AcademicGroup findByGroupNumber(String number) {
        return getHibernateTemplate().execute(session ->
                (AcademicGroup) session.createQuery("from AcademicGroup a" +
                " where a.name = :num")
                .setParameter("num", number)
                .uniqueResult());
    }
}
