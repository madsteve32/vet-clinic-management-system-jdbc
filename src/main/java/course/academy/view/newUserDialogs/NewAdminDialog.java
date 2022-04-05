package course.academy.view.newUserDialogs;

import course.academy.entities.Administrator;
import course.academy.entities.enums.Gender;
import course.academy.entities.enums.Role;
import course.academy.view.EntityDialog;

import java.util.Scanner;

public class NewAdminDialog implements EntityDialog<Administrator> {
    public static Scanner scanner = new Scanner(System.in);
    public final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public Administrator input() {
        Administrator admin = new Administrator();
        while (admin.getFirstName() == null) {
            System.out.println("Please write first name:");
            String firstName = scanner.nextLine();
            if (firstName.length() < 2) {
                System.out.println("Error: First name should be at least 2 characters.");
            } else {
                admin.setFirstName(firstName);
            }
        }
        while (admin.getLastName() == null) {
            System.out.println("Please write last name:");
            String lastName = scanner.nextLine();
            if (lastName.length() < 2) {
                System.out.println("Error: Last name should be at least 2 characters");
            } else {
                admin.setLastName(lastName);
            }
        }
        while (admin.getEmail() == null) {
            System.out.println("Please write email:");
            String email = scanner.nextLine();
            if (!email.matches(EMAIL_REGEX)) {
                System.out.println("Error: Email must be valid please try again.");
            } else {
                admin.setEmail(email);
            }
        }
        while (admin.getTelNumber() == null) {
            System.out.println("Please write telephone number:");
            String telNumber = scanner.nextLine();
            if (telNumber.length() < 10) {
                System.out.println("Error: Telephone number must contain 10 digits.");
            } else {
                admin.setTelNumber(telNumber);
            }
        }
        while (admin.getUsername() == null) {
            System.out.println("Please write username:");
            String username = scanner.nextLine();
            if (username.length() < 3) {
                System.out.println("Error: Username should be at least 3 characters.");
            } else {
                admin.setUsername(username);
            }
        }
        while (admin.getPassword() == null) {
            System.out.println("Please write password:");
            String password = scanner.nextLine();
            if (password.length() < 6) {
                System.out.println("Error: Password should be at least 6 characters.");
            } else {
                admin.setPassword(password);
            }
        }
        while (admin.getGender() == null) {
            System.out.println("Please choose gender: 'MALE' or 'FEMALE':");
            String role = scanner.nextLine();
            if (role.equals("MALE")) {
                admin.setGender(Gender.MALE);
            } else {
                admin.setGender(Gender.FEMALE);
            }
        }
        admin.setRole(Role.ADMIN);
        return admin;
    }
}
