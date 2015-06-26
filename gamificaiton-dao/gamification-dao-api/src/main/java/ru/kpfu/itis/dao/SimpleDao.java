package ru.kpfu.itis.dao;

import org.hibernate.SessionFactory;
import ru.kpfu.itis.model.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

public interface SimpleDao {

    void configure(SessionFactory sessionFactory);

    <D> D findById(Class<D> aClass, Serializable aId);

    <D> List<D> fetchAll(Class<D> aClass);

    <D> D findByField(Class<D> aClass, String aFieldName, Object value);

    <D> List<D> fetchByField(Class<D> aClass, String aFieldName, Object value);

    <E extends IdentifiedEntity> E loadById(Class<E> aClass, Serializable aId);

    /**
     * Создаёт новую сущность
     */
    <D extends IdentifiedEntity> Serializable save(D aEntity);

    /**
     * Создаёт сущность, если её нет в бд, в противном случае
     */
    <E extends IdentifiedEntity> void saveOrUpdate(E aEntity);

    <E extends IdentifiedEntity> void update(E aEntity);

    <E extends IdentifiedEntity> void delete(E aEntity);

    <E extends IdentifiedEntity> void evict(E aEntity);

    void flush();

    void clearSession();

}
