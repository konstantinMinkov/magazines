package com.kpi.magazines.dao.basic.interfaces;

import java.util.List;

/**
 * Created by Konstantin Minkov on 26.06.2016.
 */
public interface GenericDao<T> {

    /**
     * Creates row in the database with info from obj.
     * @param obj - Object to create.
     * @return - true, if created.
     */
    boolean create(T obj);

    /**
     * Updates row with info in object by id of that object.
     * @param obj - Object to update.
     * @return - true, if updated.
     */
    boolean update(T obj);

    /**
     * Deletes row by id of obj.
     * @param obj - Object to delete.
     * @return - true, if deleted.
     */
    boolean delete(T obj);
    T findById(int id);
    List<T> findAll();
    List<T> findAll(int limit, int offset);
}
