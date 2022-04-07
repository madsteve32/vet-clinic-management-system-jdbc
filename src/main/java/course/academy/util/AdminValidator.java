package course.academy.util;

import course.academy.entities.Administrator;
import course.academy.exception.ConstraintViolation;
import course.academy.exception.ConstraintViolationException;
import course.academy.exception.InvalidEntityDataException;

import java.util.ArrayList;
import java.util.List;

public class AdminValidator {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{8,20})";
    public void validate(Administrator admin) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();
        int firstNameLength = admin.getFirstName().trim().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            violations.add(
                    new ConstraintViolation(admin.getClass().getName(), "firstName", admin.getFirstName(),
                            "Admin first name length should be between 2 and 15 characters."));
        }

        int lastNameLength = admin.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            violations.add(
                    new ConstraintViolation(admin.getClass().getName(), "lastName", admin.getLastName(),
                            "Admin last name length should be between 2 and 15 characters."));
        }


        if (!admin.getEmail().matches(EMAIL_REGEX)) {
            violations.add(
                    new ConstraintViolation(admin.getClass().getName(), "email", admin.getEmail(),
                            "Admin email should be valid email address."));
        }

        int usernameLength = admin.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            violations.add(
                    new ConstraintViolation(admin.getClass().getName(), "username", admin.getUsername(),
                            "Admin username length should be between 2 and 15 characters."));
        }

        if (!admin.getPassword().matches(PASSWORD_REGEX)) {
            violations.add(
                    new ConstraintViolation(admin.getClass().getName(), "password", admin.getPassword(),
                            "Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit"));
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid admin field", violations);
        }
    }
}
