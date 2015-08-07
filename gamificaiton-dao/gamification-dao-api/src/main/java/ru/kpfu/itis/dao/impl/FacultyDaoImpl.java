package ru.kpfu.itis.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.FacultyDao;
import ru.kpfu.itis.dao.base.AbstractDaoImpl;
import ru.kpfu.itis.model.Faculty;

/**
 * Created by Rigen on 20.07.15.
 */
@Repository
public class FacultyDaoImpl extends AbstractDaoImpl<Faculty, Long> implements FacultyDao {

    protected FacultyDaoImpl() {
        super(Faculty.class);
    }

    @Override
    public Faculty findByName(String name) {
        return (Faculty) getCurrentSession().createCriteria(Faculty.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
    }

}
