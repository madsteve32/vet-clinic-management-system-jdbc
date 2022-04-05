package course.academy.view.updateUserDialogs;

import course.academy.entities.Client;
import course.academy.view.EntityDialog;

import java.util.Scanner;

public class UpdateClientDialog implements EntityDialog<Client> {
    public static Scanner scanner = new Scanner(System.in);

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
        return client;
    }
}
