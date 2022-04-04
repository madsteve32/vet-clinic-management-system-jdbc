package service.impl;

import dao.ExaminationRepository;
import entities.Examination;
import exception.InvalidEntityDataException;
import service.ExaminationService;

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
    public Collection<Examination> findAll() {
        return examinationRepository.findAll();
    }

    @Override
    public Examination addExamination(Examination examination) throws InvalidEntityDataException {
        if (examination.getDate().isBefore(LocalDate.now())) {
            throw new InvalidEntityDataException("Examination date cannot be in the past.");
        }
        Examination newExamination = examinationRepository.create(examination);
        examinationRepository.save();
        return newExamination;
    }
}
