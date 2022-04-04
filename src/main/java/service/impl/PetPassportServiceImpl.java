package service.impl;

import dao.PetPassportRepository;
import entities.PetPassport;
import exception.NonexistingEntityException;
import service.PetPassportService;

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
    public Collection<PetPassport> findAll() {
        return passportRepository.findAll();
    }

    @Override
    public PetPassport getPassportById(Long id) throws NonexistingEntityException {
        return passportRepository.findById(id);
    }

    @Override
    public PetPassport addPassport(PetPassport passport) {
        PetPassport newPassport = passportRepository.create(passport);
        passportRepository.save();
        return newPassport;
    }

    @Override
    public PetPassport updatePassport(PetPassport passport) throws NonexistingEntityException {
        PetPassport updatedPassport = passportRepository.update(passport);
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
