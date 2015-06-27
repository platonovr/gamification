package ru.kpfu.itis.processing;


import ru.kpfu.itis.model.IdentifiedEntity;

import java.io.Serializable;

/**
 * @author Сайфуллин А.М.
 * @date: 14.01.15 13:12
 */
public interface SimpleService {

    <D> D findById(Class<D> aClass, Serializable aId);

    <E extends IdentifiedEntity> E load(Class<E> aClass, Serializable aId);

    <D extends IdentifiedEntity> Serializable save(D aEntity);

    <D extends IdentifiedEntity> void saveOrUpdate(D aEntity);

    <D extends IdentifiedEntity> void delete(D aEntity);
}
