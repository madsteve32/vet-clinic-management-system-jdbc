package service.impl;

import dao.AdministratorRepository;
import entities.Administrator;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;
import service.AdministratorService;

import java.util.Collection;

public class AdministratorServiceImpl implements AdministratorService {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{8,20})";
    private final AdministratorRepository adminRepository;

    public AdministratorServiceImpl(AdministratorRepository adminRepository) {
        this.adminRepository = adminRepository;
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
    public Collection<Administrator> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Administrator getAdminById(Long id) throws NonexistingEntityException {
        return adminRepository.findById(id);
    }

    @Override
    public Administrator addAdmin(Administrator admin) throws InvalidEntityDataException {
        int firstNameLength = admin.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }

        int lastNameLength = admin.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }


        if (!admin.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidEntityDataException("Email must be valid.");
        }

        int usernameLength = admin.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }

        if (!admin.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidEntityDataException("Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit");
        }
        Administrator newAdmin = adminRepository.create(admin);
        adminRepository.save();
        return newAdmin;
    }

    @Override
    public Administrator updateAdmin(Administrator admin) throws NonexistingEntityException, InvalidEntityDataException {
        int firstNameLength = admin.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }

        int lastNameLength = admin.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }

        if (!admin.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidEntityDataException("Email must be valid.");
        }

        int usernameLength = admin.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }

        if (!admin.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidEntityDataException("Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit");
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
    public Administrator findByUsername(String username) {
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
