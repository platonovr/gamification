package ru.kpfu.itis.processing;


import ru.kpfu.itis.model.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

public interface SimpleService {

    <D> D findById(Class<D> aClass, Serializable aId);

    <E extends IdentifiedEntity> E load(Class<E> aClass, Serializable aId);

    <D extends IdentifiedEntity> Serializable save(D aEntity);

    <D extends IdentifiedEntity> void saveOrUpdate(D aEntity);

    <D extends IdentifiedEntity> void delete(D aEntity);

    <D> List<D> fetchAll(Class<D> aClass);
}
