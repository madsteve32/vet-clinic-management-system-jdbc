package course.academy.dao;

import course.academy.entities.PetPassport;
import course.academy.exception.NonexistingEntityException;

public interface PetPassportRepository extends PersistableRepository<Long, PetPassport> {
    PetPassport findByPetId(Long id) throws NonexistingEntityException;
}
