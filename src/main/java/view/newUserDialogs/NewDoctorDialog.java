package view.newUserDialogs;

import entities.Doctor;
import entities.enums.Gender;
import view.EntityDialog;

import java.util.ArrayList;
import java.util.Scanner;

import static entities.enums.Role.DOCTOR;

public class NewDoctorDialog implements EntityDialog<Doctor> {
    public static Scanner scanner = new Scanner(System.in);
    public final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

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
        while (doctor.getEmail() == null) {
            System.out.println("Please write email:");
            String email = scanner.nextLine();
            if (!email.matches(EMAIL_REGEX)) {
                System.out.println("Error: Email must be valid please try again.");
            } else {
                doctor.setEmail(email);
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
        while (doctor.getPassword() == null) {
            System.out.println("Please write password:");
            String password = scanner.nextLine();
            if (password.length() < 6) {
                System.out.println("Error: Password should be at least 6 characters.");
            } else {
                doctor.setPassword(password);
            }
        }
        while (doctor.getGender() == null) {
            System.out.println("Please choose gender: 'MALE' or 'FEMALE':");
            String role = scanner.nextLine();
            if (role.equals("MALE")) {
                doctor.setGender(Gender.MALE);
            } else {
                doctor.setGender(Gender.FEMALE);
            }
        }
        doctor.setRole(DOCTOR);
        doctor.setAppointments(new ArrayList<>());
        return doctor;
    }
}
