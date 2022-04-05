package course.academy.view.updateUserDialogs;

import course.academy.entities.Administrator;
import course.academy.view.EntityDialog;

import java.util.Scanner;

public class UpdateAdminDialog implements EntityDialog<Administrator> {
    public static Scanner scanner = new Scanner(System.in);

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
        return admin;
    }
}
