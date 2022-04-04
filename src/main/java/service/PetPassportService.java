package service;

import entities.PetPassport;
import exception.NonexistingEntityException;

import java.util.Collection;

public interface PetPassportService {
    void loadData();
    void saveData();
    Collection<PetPassport> findAll();
    PetPassport getPassportById(Long id) throws NonexistingEntityException;
    PetPassport addPassport(PetPassport passport);
    PetPassport updatePassport(PetPassport passport) throws NonexistingEntityException;
    PetPassport deletePassportById(Long id) throws NonexistingEntityException;
}
