package course.academy.util;

import course.academy.entities.Doctor;
import course.academy.exception.ConstraintViolation;
import course.academy.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class DoctorValidator {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{8,20})";
    public void validate(Doctor doctor) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();
        int firstNameLength = doctor.getFirstName().trim().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            violations.add(
                    new ConstraintViolation(doctor.getClass().getName(), "firstName", doctor.getFirstName(),
                            "Doctor first name length should be between 2 and 15 characters."));
        }

        int lastNameLength = doctor.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            violations.add(
                    new ConstraintViolation(doctor.getClass().getName(), "lastName", doctor.getLastName(),
                            "Doctor last name length should be between 2 and 15 characters."));
        }


        if (!doctor.getEmail().matches(EMAIL_REGEX)) {
            violations.add(
                    new ConstraintViolation(doctor.getClass().getName(), "email", doctor.getEmail(),
                            "Doctor email should be valid email address."));
        }

        int usernameLength = doctor.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            violations.add(
                    new ConstraintViolation(doctor.getClass().getName(), "username", doctor.getUsername(),
                            "Doctor username length should be between 2 and 15 characters."));
        }

        if (!doctor.getPassword().matches(PASSWORD_REGEX)) {
            violations.add(
                    new ConstraintViolation(doctor.getClass().getName(), "password", doctor.getPassword(),
                            "Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit"));
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid doctor field", violations);
        }
    }
}
