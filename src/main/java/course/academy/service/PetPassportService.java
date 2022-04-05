package course.academy.service;

import course.academy.entities.PetPassport;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;

public interface PetPassportService {
    void loadData();
    void saveData();
    Collection<PetPassport> findAll() throws EntityPersistenceException;
    PetPassport getPassportById(Long id) throws NonexistingEntityException;
    PetPassport addPassport(PetPassport passport) throws EntityPersistenceException;
    PetPassport updatePassport(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException;
    PetPassport deletePassportById(Long id) throws NonexistingEntityException;
}
