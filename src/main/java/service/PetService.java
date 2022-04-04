package service;

import entities.Pet;
import exception.NonexistingEntityException;

public interface PetService {
    void loadData();
    void saveData();
    Pet getPetById(Long id) throws NonexistingEntityException;
    Pet addPet(Pet pet);
    Pet updatePet(Pet pet) throws NonexistingEntityException;
    Pet deletePetById(Long id) throws NonexistingEntityException;
}
