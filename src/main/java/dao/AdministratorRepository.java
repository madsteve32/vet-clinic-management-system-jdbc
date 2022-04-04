package dao;

import entities.Administrator;
import exception.NonexistingEntityException;

/**
 * Public interface for managing lifecycle of Administrator objects.
 */
public interface AdministratorRepository extends PersistableRepository<Long, Administrator> {
    Administrator deleteById(Long id) throws NonexistingEntityException;
}
