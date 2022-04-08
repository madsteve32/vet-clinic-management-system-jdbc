package course.academy.dao.impl;

import course.academy.dao.ExaminationRepository;
import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class ExaminationRepositoryJdbc implements ExaminationRepository {
    @Override
    public Collection<Examination> findAll() throws EntityPersistenceException {
        return null;
    }

    @Override
    public Examination findById(Long id) throws NonexistingEntityException {
        return null;
    }

    @Override
    public Examination create(Examination entity) throws EntityPersistenceException {
        return null;
    }

    @Override
    public Examination update(Examination entity) throws NonexistingEntityException, EntityPersistenceException {
        return null;
    }

    @Override
    public Examination deleteById(Long id) throws NonexistingEntityException {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public void addAll(Collection<Examination> entities) {

    }

    @Override
    public void clear() {

    }
}
