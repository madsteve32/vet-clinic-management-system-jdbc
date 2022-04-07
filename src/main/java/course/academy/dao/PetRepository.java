package course.academy.dao;

import course.academy.entities.Pet;
import course.academy.exception.NonexistingEntityException;

public interface PetRepository extends PersistableRepository<Long, Pet> {
    Pet findByClientId(Long id) throws NonexistingEntityException;
}
