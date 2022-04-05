package course.academy.service;

import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;

import java.util.Collection;

public interface ExaminationService {
    void loadData();
    void saveData();
    Collection<Examination> findAll() throws EntityPersistenceException;
    Examination addExamination(Examination examination) throws InvalidEntityDataException, EntityPersistenceException;
}
