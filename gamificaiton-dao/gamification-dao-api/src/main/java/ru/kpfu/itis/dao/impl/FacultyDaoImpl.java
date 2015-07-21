package ru.kpfu.itis.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dao.FacultyDao;
import ru.kpfu.itis.model.Faculty;

/**
 * Created by Rigen on 20.07.15.
 */
@Repository
public class FacultyDaoImpl extends SimpleDaoImpl implements FacultyDao {

    @Override
    public Faculty findByName(String name) {
        return getHibernateTemplate().execute((aSession) ->
                (Faculty) aSession.createCriteria(Faculty.class)
                        .add(Restrictions.eq("name", name)).uniqueResult());
    }

}
