package view.newUserDialogs;

import entities.Client;
import entities.enums.Gender;
import view.EntityDialog;

import java.util.Scanner;

import static entities.enums.Role.CLIENT;

public class NewClientDialog implements EntityDialog<Client> {
    public static Scanner scanner = new Scanner(System.in);
    public final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public Client input() {
        Client client = new Client();
        while (client.getFirstName() == null) {
            System.out.println("Please write first name:");
            String firstName = scanner.nextLine();
            if (firstName.length() < 2) {
                System.out.println("Error: First name should be at least 2 characters.");
            } else {
                client.setFirstName(firstName);
            }
        }
        while (client.getLastName() == null) {
            System.out.println("Please write last name:");
            String lastName = scanner.nextLine();
            if (lastName.length() < 2) {
                System.out.println("Error: Last name should be at least 2 characters");
            } else {
                client.setLastName(lastName);
            }
        }
        while (client.getEmail() == null) {
            System.out.println("Please write email:");
            String email = scanner.nextLine();
            if (!email.matches(EMAIL_REGEX)) {
                System.out.println("Error: Email must be valid please try again.");
            } else {
                client.setEmail(email);
            }
        }
        while (client.getTelNumber() == null) {
            System.out.println("Please write telephone number:");
            String telNumber = scanner.nextLine();
            if (telNumber.length() < 10) {
                System.out.println("Error: Telephone number must contain 10 digits.");
            } else {
                client.setTelNumber(telNumber);
            }
        }
        while (client.getUsername() == null) {
            System.out.println("Please write username:");
            String username = scanner.nextLine();
            if (username.length() < 3) {
                System.out.println("Error: Username should be at least 3 characters.");
            } else {
                client.setUsername(username);
            }
        }
        while (client.getPassword() == null) {
            System.out.println("Please write password:");
            String password = scanner.nextLine();
            if (password.length() < 6) {
                System.out.println("Error: Password should be at least 6 characters.");
            } else {
                client.setPassword(password);
            }
        }
        while (client.getGender() == null) {
            System.out.println("Please choose gender: 'MALE' or 'FEMALE':");
            String role = scanner.nextLine();
            if (role.equals("MALE")) {
                client.setGender(Gender.MALE);
            } else {
                client.setGender(Gender.FEMALE);
            }
        }
        client.setRole(CLIENT);
        return client;
    }
}
