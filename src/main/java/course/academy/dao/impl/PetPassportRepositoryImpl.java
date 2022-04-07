package course.academy.dao.impl;

import course.academy.dao.PetPassportRepository;
import course.academy.entities.PetPassport;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PetPassportRepositoryImpl implements PetPassportRepository {
    Map<Long, PetPassport> passports = new HashMap<>();

    @Override
    public Collection<PetPassport> findAll() {
        return passports.values();
    }

    @Override
    public PetPassport findById(Long id) throws NonexistingEntityException {
        PetPassport passport = passports.get(id);
        if (passport == null) {
            throw new NonexistingEntityException("Passport with ID'" + id + "' does not exist.");
        }
        return passport;
    }

    @Override
    public PetPassport create(PetPassport passport) {
        passports.put(passport.getId(), passport);
        return passport;
    }

    @Override
    public PetPassport update(PetPassport passport) throws NonexistingEntityException {
        PetPassport oldPassport = findById(passport.getId());
        if (oldPassport == null) {
            throw new NonexistingEntityException("Passport with ID'" + passport.getId() + "' does not exist.");
        }
        passports.put(passport.getId(), passport);
        return passport;
    }

    @Override
    public PetPassport deleteById(Long id) throws NonexistingEntityException {
        PetPassport passport = passports.remove(id);
        if (passport == null) {
            throw new NonexistingEntityException("Passport with ID'" + id + "' does not exist.");
        }
        return passport;
    }

    @Override
    public long count() {
        return passports.size();
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public void addAll(Collection<PetPassport> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public PetPassport findByPetId(Long id) throws NonexistingEntityException {
        return null;
    }
}
