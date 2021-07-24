package com.esmc.mcnp.services.base;

import java.util.List;

public interface BaseService<T, K> {
	
	/**
	 * Add entity.
	 *
	 * @param entity
	 *            to add
	 */
	T add(T entity);

	/**
	 * Update entity.
	 *
	 * @param entity
	 *            to update
	 */
	T update(T entity);

	/**
	 * Delete entity.
	 *
	 * @param entity
	 *            to delete
	 */
	void delete(T entity);

	/**
	 * Delete entity by its id.
	 *
	 * @param id
	 *            entity id to delete
	 */
	void deleteById(K id);
	
	/**
     * Delete many entities provided with list of ids.
     *
     * @param ids
     *            to delete
     */
    void deleteMany(Iterable<K> ids);

    /**
     * Get entity by ID.
     *
     * @param id Id
     */
    T findById(K id);

    /**
     * @return count of all entities in db.
     */
    long count();
    
    List<T> list();

}
