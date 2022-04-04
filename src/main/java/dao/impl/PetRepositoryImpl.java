package dao.impl;

import dao.PetRepository;
import entities.Pet;
import exception.NonexistingEntityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PetRepositoryImpl implements PetRepository {
    Map<Long, Pet> pets = new HashMap<>();

    @Override
    public Collection<Pet> findAll() {
        return pets.values();
    }

    @Override
    public Pet findById(Long id) throws NonexistingEntityException {
        Pet pet = pets.get(id);
        if (pet == null) {
            throw new NonexistingEntityException("Pet with ID'" + id + "' does not exist.");
        }
        return pet;
    }

    @Override
    public Pet create(Pet pet) {
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet update(Pet pet) throws NonexistingEntityException {
        Pet oldPet = findById(pet.getId());
        if (oldPet == null) {
            throw new NonexistingEntityException("Pet with ID'" + pet.getId() + "' does not exist.");
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet deleteById(Long id) throws NonexistingEntityException {
        Pet pet = pets.remove(id);
        if (pet == null) {
            throw new NonexistingEntityException("Pet with ID'" + id + "' does not exist.");
        }
        return pet;
    }

    @Override
    public long count() {
        return pets.size();
    }

    @Override
    public void addAll(Collection<Pet> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
