package ru.kpfu.itis.filter;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

/**
 * Created by timur on 24.06.15.
 */
public class AutoFlushOpenSessionInViewFilter extends OpenSessionInViewFilter {

    @Override
    protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        try {
            Session session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.AUTO);
            return session;
        } catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
        }
    }
}
