package view;

import entities.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static entities.enums.ExaminationType.*;

public class NewAppointmentDialog implements EntityDialog<Appointment> {
    public static Scanner scanner = new Scanner(System.in);

    @Override
    public Appointment input() {
        Appointment appointment = new Appointment();
        while (appointment.getExaminationType() == null) {
            System.out.println("Please choose examination type: (DEWORMING, VACCINATION, CONTROL_EXAMINATION)");
            String examinationType = scanner.nextLine();
            if (examinationType.equals("DEWORMING")) {
                appointment.setExaminationType(DEWORMING);
            } else if (examinationType.equals("VACCINATION")) {
                appointment.setExaminationType(VACCINATION);
            } else {
                appointment.setExaminationType(CONTROL_EXAMINATION);
            }
        }
        while (appointment.getChosenDateTime() == null) {
            System.out.println("Please write chosen date and time in format: (dd/MM/yyyy HH:mm)");
            String strDate = scanner.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(strDate,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            if (dateTime.isBefore(LocalDateTime.now())) {
                System.out.println("Error: Chosen date and time cannot be in the past.");
            } else {
                appointment.setChosenDateTime(dateTime);
            }
        }
        return appointment;
    }
}
