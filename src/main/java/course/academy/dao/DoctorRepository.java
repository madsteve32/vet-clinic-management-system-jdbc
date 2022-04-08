package course.academy.dao;

import course.academy.entities.Doctor;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;

public interface DoctorRepository extends PersistableRepository<Long, Doctor> {
    public Doctor updateByAdmin(Doctor doctor) throws NonexistingEntityException, EntityPersistenceException;
}
