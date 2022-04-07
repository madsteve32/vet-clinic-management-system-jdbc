package course.academy.service.impl;

import course.academy.dao.AdministratorRepository;
import course.academy.entities.Administrator;
import course.academy.exception.ConstraintViolationException;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.AdministratorService;
import course.academy.util.AdminValidator;

import java.util.Collection;

public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository adminRepository;
    private final AdminValidator adminValidator;

    public AdministratorServiceImpl(AdministratorRepository adminRepository, AdminValidator adminValidator) {
        this.adminRepository = adminRepository;
        this.adminValidator = adminValidator;
    }


    @Override
    public void loadData() {
        adminRepository.load();
    }

    @Override
    public void saveData() {
        adminRepository.save();
    }

    @Override
    public Collection<Administrator> findAll() throws EntityPersistenceException {
        return adminRepository.findAll();
    }

    @Override
    public Administrator getAdminById(Long id) throws NonexistingEntityException {
        return adminRepository.findById(id);
    }

    @Override
    public Administrator addAdmin(Administrator admin) throws InvalidEntityDataException, EntityPersistenceException {
        try {
            adminValidator.validate(admin);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityDataException(
                    String.format("Error creating admin '%s'", admin.getUsername()), e);
        }
        Administrator newAdmin = adminRepository.create(admin);
        adminRepository.save();
        return newAdmin;
    }

    @Override
    public Administrator updateAdmin(Administrator admin) throws NonexistingEntityException, InvalidEntityDataException, EntityPersistenceException {
        try {
            adminValidator.validate(admin);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityDataException(
                    String.format("Error updating admin '%s'", admin.getUsername()), e);
        }
        Administrator updatedAdmin = adminRepository.update(admin);
        adminRepository.save();
        return updatedAdmin;
    }

    @Override
    public Administrator deleteAdminById(Long id) throws NonexistingEntityException {
        Administrator deletedAdministrator = adminRepository.deleteById(id);
        adminRepository.save();
        return deletedAdministrator;
    }

    @Override
    public Administrator findByUsername(String username) throws EntityPersistenceException {
        Collection<Administrator> allAdmins = adminRepository.findAll();
        for (Administrator admin : allAdmins) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public long count() {
        return adminRepository.count();
    }
}
