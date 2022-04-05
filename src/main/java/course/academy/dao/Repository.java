package course.academy.dao;

import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;

/**
 * Public interface for managing lifecycle of entity objects.
 */
public interface Repository<K, V extends Identifiable<K>> {
    /**
     * Find all course.academy.entities in repository
     * @return list of all course.academy.entities
     */
    Collection<V> findAll() throws EntityPersistenceException;

    /**
     * Find user by id
     * @param id the entity id
     * @return the entity with given id or null if id not found in repository
     */
    V findById(K id) throws NonexistingEntityException;

    /**
     * Create new entity
     * @param entity to be created
     * @return the entity that was created
     */
    V create(V entity) throws EntityPersistenceException;
    void addAll(Collection<V> entities);
    void clear();

    /**
     * Update given entity information
     * @param entity with the new information
     * @return updated entity
     */
    V update(V entity) throws NonexistingEntityException, EntityPersistenceException;

    /**
     * Delete entity bi given id
     * @param id the entity id
     * @return the entity with given id or null if id not found in repository
     */
    V deleteById(K id) throws NonexistingEntityException;

    /**
     * Give the repository size count
     * @return repository count
     */
    long count();
}
