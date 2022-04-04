package view.updateUserDialogs;

import entities.Doctor;
import view.EntityDialog;

import java.util.Scanner;

public class UpdateDoctorDialog implements EntityDialog<Doctor> {
    public static Scanner scanner = new Scanner(System.in);

    @Override
    public Doctor input() {
        Doctor doctor = new Doctor();
        while (doctor.getFirstName() == null) {
            System.out.println("Please write first name:");
            String firstName = scanner.nextLine();
            if (firstName.length() < 2) {
                System.out.println("Error: First name should be at least 2 characters.");
            } else {
                doctor.setFirstName(firstName);
            }
        }
        while (doctor.getLastName() == null) {
            System.out.println("Please write last name:");
            String lastName = scanner.nextLine();
            if (lastName.length() < 2) {
                System.out.println("Error: Last name should be at least 2 characters");
            } else {
                doctor.setLastName(lastName);
            }
        }
        while (doctor.getTelNumber() == null) {
            System.out.println("Please write telephone number:");
            String telNumber = scanner.nextLine();
            if (telNumber.length() < 10) {
                System.out.println("Error: Telephone number must contain 10 digits.");
            } else {
                doctor.setTelNumber(telNumber);
            }
        }
        while (doctor.getUsername() == null) {
            System.out.println("Please write username:");
            String username = scanner.nextLine();
            if (username.length() < 3) {
                System.out.println("Error: Username should be at least 3 characters.");
            } else {
                doctor.setUsername(username);
            }
        }
        return doctor;
    }
}
