package ru.kpfu.itis.base;


import ru.kpfu.itis.model.IdentifiedEntity;

import java.io.Serializable;

public interface GenericDao<T extends IdentifiedEntity> {

    <Key extends Serializable> T getEntity(Class<T> entityClass, Key entityId);

    T save(Class<T> entityClass, T entity);
}
