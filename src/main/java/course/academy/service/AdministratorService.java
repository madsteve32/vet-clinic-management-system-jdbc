package course.academy.service;

import course.academy.entities.Administrator;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;

public interface AdministratorService {
    void loadData();
    void saveData();
    Collection<Administrator> findAll() throws EntityPersistenceException;
    Administrator getAdminById(Long id) throws NonexistingEntityException;
    Administrator addAdmin(Administrator admin) throws InvalidEntityDataException, EntityPersistenceException;
    Administrator updateAdmin(Administrator admin) throws NonexistingEntityException, InvalidEntityDataException, EntityPersistenceException;
    Administrator deleteAdminById(Long id) throws NonexistingEntityException;
    Administrator findByUsername(String username) throws EntityPersistenceException;
    long count();
}
