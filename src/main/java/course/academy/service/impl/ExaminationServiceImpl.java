package course.academy.service.impl;

import course.academy.dao.ExaminationRepository;
import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
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
    public Examination getExaminationById(Long id) throws NonexistingEntityException {
        return examinationRepository.findById(id);
    }

    @Override
    public Examination addExamination(Examination examination) throws InvalidEntityDataException, EntityPersistenceException {
        Collection<Examination> examinations = examinationRepository.findAll();
        for (Examination bookedExamination : examinations) {
            if (bookedExamination.getDate().equals(examination.getDate())) {
                throw new InvalidEntityDataException("Examination date is already taken please choose other date.");
            }
        }
        if (examination.getDate().isBefore(LocalDate.now())) {
            throw new InvalidEntityDataException("Examination date cannot be in the past.");
        }

        Examination newExamination = examinationRepository.create(examination);
        examinationRepository.save();
        return newExamination;
    }

    @Override
    public Examination updateExamination(Examination examination) throws NonexistingEntityException, EntityPersistenceException {
        return examinationRepository.update(examination);
    }

    @Override
    public Examination deleteExaminationById(Long id) throws NonexistingEntityException {
        return examinationRepository.deleteById(id);
    }

    @Override
    public long count() {
        return examinationRepository.count();
    }
}
