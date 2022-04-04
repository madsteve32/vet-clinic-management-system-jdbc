package service;

import entities.Examination;
import exception.InvalidEntityDataException;

import java.util.Collection;

public interface ExaminationService {
    void loadData();
    void saveData();
    Collection<Examination> findAll();
    Examination addExamination(Examination examination) throws InvalidEntityDataException;
}
