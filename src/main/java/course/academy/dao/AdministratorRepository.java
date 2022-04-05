package course.academy.dao;

import course.academy.entities.Administrator;
import course.academy.exception.NonexistingEntityException;

/**
 * Public interface for managing lifecycle of Administrator objects.
 */
public interface AdministratorRepository extends PersistableRepository<Long, Administrator> {
    Administrator deleteById(Long id) throws NonexistingEntityException;
}
