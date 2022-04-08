package course.academy.service;

import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;

public interface ExaminationService {
    void loadData();
    void saveData();
    Collection<Examination> findAll() throws EntityPersistenceException;
    Examination getExaminationById(Long id) throws NonexistingEntityException;
    Examination addExamination(Examination examination) throws InvalidEntityDataException, EntityPersistenceException;
    Examination updateExamination(Examination examination) throws NonexistingEntityException, EntityPersistenceException;
    Examination deleteExaminationById(Long id) throws NonexistingEntityException;
    long count();
}
