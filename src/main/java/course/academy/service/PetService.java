package course.academy.service;

import course.academy.entities.Pet;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;

public interface PetService {
    void loadData();
    void saveData();
    Pet getPetById(Long id) throws NonexistingEntityException;
    Pet addPet(Pet pet) throws EntityPersistenceException;
    Pet updatePet(Pet pet) throws NonexistingEntityException, EntityPersistenceException;
    Pet deletePetById(Long id) throws NonexistingEntityException;
}
