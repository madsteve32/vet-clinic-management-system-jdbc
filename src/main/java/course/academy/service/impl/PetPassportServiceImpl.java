package course.academy.service.impl;

import course.academy.dao.PetPassportRepository;
import course.academy.entities.PetPassport;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.PetPassportService;

import java.util.Collection;

public class PetPassportServiceImpl implements PetPassportService {
    private final PetPassportRepository passportRepository;

    public PetPassportServiceImpl(PetPassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    public void loadData() {
        passportRepository.load();
    }

    @Override
    public void saveData() {
        passportRepository.save();
    }

    @Override
    public Collection<PetPassport> findAll() throws EntityPersistenceException {
        return passportRepository.findAll();
    }

    @Override
    public PetPassport getPassportById(Long id) throws NonexistingEntityException {
        return passportRepository.findById(id);
    }

    @Override
    public PetPassport getPassportByPetId(Long id) throws NonexistingEntityException {
        return passportRepository.findByPetId(id);
    }

    @Override
    public PetPassport addPassport(PetPassport passport) throws EntityPersistenceException {
        PetPassport newPassport = passportRepository.create(passport);
        passportRepository.save();
        return newPassport;
    }

    @Override
    public PetPassport updatePassport(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException {
        PetPassport updatedPassport = passportRepository.update(passport);
        passportRepository.save();
        return updatedPassport;
    }

    @Override
    public PetPassport updatePassportDewormingDate(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException {
        PetPassport updatedPassport = passportRepository.updateDewormingDate(passport);
        passportRepository.save();
        return updatedPassport;
    }

    @Override
    public PetPassport updatePassportVaccinationDate(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException {
        PetPassport updatedPassport = passportRepository.updateVaccinationDate(passport);
        passportRepository.save();
        return updatedPassport;
    }

    @Override
    public PetPassport updatePassportExaminationDate(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException {
        PetPassport updatedPassport = passportRepository.updateExaminationDate(passport);
        passportRepository.save();
        return updatedPassport;
    }

    @Override
    public PetPassport deletePassportById(Long id) throws NonexistingEntityException {
        PetPassport deletedPassport = passportRepository.deleteById(id);
        passportRepository.save();
        return deletedPassport;
    }
}
