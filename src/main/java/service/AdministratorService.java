package service;

import entities.Administrator;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;

import java.util.Collection;

public interface AdministratorService {
    void loadData();
    void saveData();
    Collection<Administrator> findAll();
    Administrator getAdminById(Long id) throws NonexistingEntityException;
    Administrator addAdmin(Administrator admin) throws InvalidEntityDataException;
    Administrator updateAdmin(Administrator admin) throws NonexistingEntityException, InvalidEntityDataException;
    Administrator deleteAdminById(Long id) throws NonexistingEntityException;
    Administrator findByUsername(String username);
    long count();
}
