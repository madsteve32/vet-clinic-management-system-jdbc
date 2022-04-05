package course.academy.service.impl;

import course.academy.dao.ExaminationRepository;
import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.service.ExaminationService;

import java.time.LocalDate;
import java.util.Collection;

public class ExaminationServiceImpl implements ExaminationService {
    private final ExaminationRepository examinationRepository;

    public ExaminationServiceImpl(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Override
    public void loadData() {
        examinationRepository.load();
    }

    @Override
    public void saveData() {
        examinationRepository.save();
    }

    @Override
    public Collection<Examination> findAll() throws EntityPersistenceException {
        return examinationRepository.findAll();
    }

    @Override
    public Examination addExamination(Examination examination) throws InvalidEntityDataException, EntityPersistenceException {
        if (examination.getDate().isBefore(LocalDate.now())) {
            throw new InvalidEntityDataException("Examination date cannot be in the past.");
        }
        Examination newExamination = examinationRepository.create(examination);
        examinationRepository.save();
        return newExamination;
    }
}
