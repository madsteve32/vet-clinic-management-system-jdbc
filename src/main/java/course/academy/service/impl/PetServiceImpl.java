package course.academy.service.impl;

import course.academy.dao.PetRepository;
import course.academy.entities.Pet;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.PetService;

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
    public Pet getPetByClientId(Long id) throws NonexistingEntityException {
        return petRepository.findByClientId(id);
    }

    @Override
    public Pet addPet(Pet pet) throws EntityPersistenceException {
        Pet newPet = petRepository.create(pet);
        petRepository.save();
        return newPet;
    }

    @Override
    public Pet updatePet(Pet pet) throws NonexistingEntityException, EntityPersistenceException {
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
