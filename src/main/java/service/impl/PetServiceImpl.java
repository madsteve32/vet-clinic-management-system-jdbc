package service.impl;

import dao.PetRepository;
import entities.Pet;
import exception.NonexistingEntityException;
import service.PetService;

public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void loadData() {
        petRepository.load();
    }

    @Override
    public void saveData() {
        petRepository.save();
    }

    @Override
    public Pet getPetById(Long id) throws NonexistingEntityException {
        return petRepository.findById(id);
    }

    @Override
    public Pet addPet(Pet pet) {
        Pet newPet = petRepository.create(pet);
        petRepository.save();
        return newPet;
    }

    @Override
    public Pet updatePet(Pet pet) throws NonexistingEntityException {
        Pet updatedPet = petRepository.update(pet);
        petRepository.save();
        return updatedPet;
    }

    @Override
    public Pet deletePetById(Long id) throws NonexistingEntityException {
        Pet deletedPet = petRepository.deleteById(id);
        petRepository.save();
        return deletedPet;
    }
}
