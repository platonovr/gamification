package ru.kpfu.itis.dao;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<E, I extends Serializable> {

    E findByField(String aFieldName, Object aValue);

    List<E> fetchByField(String aFieldName, Object aValue);

    /**
     * Save current entity in DB
     *
     * @return generated key of this entity: if entity has no in(id is null),
     * then hibernate generate id and set in in entity and return as value
     */
    I save(E entity);

    /**
     * Update(edit) current entity in DB (found by id)
     */
    void edit(E entity);

    /**
     * Find entity by id
     *
     * @param id - key of entity in DB
     */
    E findById(I id);

    /**
     * Find all rows in DB
     *
     * @return
     */
    List<E> findAll();

    /**
     * Find all rows and sort them
     *
     * @param columnName ORDER BY columnName
     * @param isAsc true -> ASC, false -> DESC
     * @return
     */
    List<E> findAllAndSortBy(String columnName, boolean isAsc);

    /**
     * SELECT * WHERE columnName LIKE prefix%
     * @param columnName
     * @param prefix
     * @return
     */
    List<E> searchFor(String columnName, String prefix);
    /**
     *
     * @param newEntity - entity to rewrite old one
     */
    void update(E newEntity);

    long count();
    /**
     * Delete entity by id
     */
    void delete(E entity);
    void delete(I id);

    /**
     * Delete all elements in your entity
     */
    void deleteAll();
}
