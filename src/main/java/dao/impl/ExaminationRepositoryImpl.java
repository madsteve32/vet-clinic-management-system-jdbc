package dao.impl;

import dao.ExaminationRepository;
import entities.Examination;
import exception.NonexistingEntityException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static entities.enums.Status.WAITING;

public class ExaminationRepositoryImpl implements ExaminationRepository {
    Map<Long, Examination> examinations = new HashMap<>();

    @Override
    public Collection<Examination> findAll() {
        return examinations.values();
    }

    @Override
    public Examination findById(Long id) throws NonexistingEntityException {
        Examination examination = examinations.get(id);
        if (examination == null) {
            throw new NonexistingEntityException("Examination with ID'" + id + "' does not exist.");
        }
        return examination;
    }

    @Override
    public Examination create(Examination examination) {
        examination.setDate(LocalDate.now());
        examination.setStatus(WAITING);
        examinations.put(examination.getId(), examination);
        return examination;
    }

    @Override
    public Examination update(Examination examination) throws NonexistingEntityException {
        Examination oldExamination = findById(examination.getId());
        if (oldExamination == null) {
            throw new NonexistingEntityException("Examination with ID'" + examination.getId() + "' does not exist.");
        }
        examinations.put(examination.getId(), examination);
        return examination;
    }

    @Override
    public Examination deleteById(Long id) throws NonexistingEntityException {
        Examination examination = examinations.remove(id);
        if (examination == null) {
            throw new NonexistingEntityException("Examination with ID'" + id + "' does not exist.");
        }
        return examination;
    }

    @Override
    public void addAll(Collection<Examination> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public long count() {
        return examinations.size();
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
