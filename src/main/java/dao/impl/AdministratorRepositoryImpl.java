package dao.impl;

import dao.AdministratorRepository;
import entities.Administrator;
import exception.NonexistingEntityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static entities.enums.Role.ADMIN;

public class AdministratorRepositoryImpl implements AdministratorRepository {
    private Map<Long, Administrator> administrators = new HashMap<>();

    @Override
    public Collection<Administrator> findAll() {
        return administrators.values();
    }

    @Override
    public Administrator findById(Long id) throws NonexistingEntityException {
        Administrator admin = administrators.get(id);
        if (admin == null) {
            throw new NonexistingEntityException("Administrator with ID'" + id + "' does not exist.");
        }
        return admin;
    }

    @Override
    public Administrator create(Administrator admin) {
        admin.setRole(ADMIN);
        administrators.put(admin.getId(), admin);
        return admin;
    }

    @Override
    public void addAll(Collection<Administrator> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Administrator update(Administrator admin) throws NonexistingEntityException {
        Administrator oldAdmin = findById(admin.getId());
        if (oldAdmin == null) {
            throw new NonexistingEntityException("Administrator with ID'" + admin.getId() + "' does not exist.");
        }
        administrators.put(admin.getId(), admin);
        return admin;
    }

    @Override
    public Administrator deleteById(Long id) throws NonexistingEntityException {
        Administrator admin = administrators.remove(id);
        if (admin == null) {
            throw new NonexistingEntityException("Administrator with ID'" + id + "' does not exist.");
        }
        return admin;
    }

    @Override
    public long count() {
        return administrators.size();
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {
    }
}
