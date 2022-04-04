package view;

import entities.Examination;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static entities.enums.Status.WAITING;

public class NewExaminationDialog implements EntityDialog<Examination> {
    public static Scanner scanner = new Scanner(System.in);

    @Override
    public Examination input() {
        Examination examination = new Examination();
        while (examination.getName() == null) {
            System.out.println("Please write name:");
            String name = scanner.nextLine();
            if (name.length() < 3) {
                System.out.println("Error: Name should be at least 3 characters.");
            } else {
                examination.setName(name);
            }
        }
        while (examination.getDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            System.out.println("Please enter a date in format (d/MM/yyyy):");
            String strDate = scanner.nextLine();
            LocalDate localDate = LocalDate.parse(strDate, formatter);
            if (localDate == null) {
                System.out.println("Error: Date should be in the format (d/MM/yyyy).");
            } else {
                examination.setDate(localDate);
            }
        }
        examination.setStatus(WAITING);
        return examination;
    }
}
