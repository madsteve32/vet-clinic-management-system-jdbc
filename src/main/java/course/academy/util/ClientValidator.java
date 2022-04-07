package course.academy.util;

import course.academy.entities.Client;
import course.academy.exception.ConstraintViolation;
import course.academy.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{8,20})";
    public void validate(Client client) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();
        int firstNameLength = client.getFirstName().trim().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            violations.add(
                    new ConstraintViolation(client.getClass().getName(), "firstName", client.getFirstName(),
                            "Client first name length should be between 2 and 15 characters."));
        }

        int lastNameLength = client.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            violations.add(
                    new ConstraintViolation(client.getClass().getName(), "lastName", client.getLastName(),
                            "Client last name length should be between 2 and 15 characters."));
        }


        if (!client.getEmail().matches(EMAIL_REGEX)) {
            violations.add(
                    new ConstraintViolation(client.getClass().getName(), "email", client.getEmail(),
                            "Client email should be valid email address."));
        }

        int usernameLength = client.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            violations.add(
                    new ConstraintViolation(client.getClass().getName(), "username", client.getUsername(),
                            "Client username length should be between 2 and 15 characters."));
        }

        if (!client.getPassword().matches(PASSWORD_REGEX)) {
            violations.add(
                    new ConstraintViolation(client.getClass().getName(), "password", client.getPassword(),
                            "Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit"));
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid client field", violations);
        }
    }
}
