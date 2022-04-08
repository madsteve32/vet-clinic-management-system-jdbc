package course.academy.dao;

import course.academy.entities.PetPassport;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;

public interface PetPassportRepository extends PersistableRepository<Long, PetPassport> {
    PetPassport findByPetId(Long id) throws NonexistingEntityException;
    PetPassport updateDewormingDate(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException;
    PetPassport updateVaccinationDate(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException;
    PetPassport updateExaminationDate(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException;
}
