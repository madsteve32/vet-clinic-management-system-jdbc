package course.academy.controller;

import course.academy.entities.*;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.*;
import course.academy.view.Menu;
import course.academy.view.NewExaminationDialog;
import course.academy.view.newUserDialogs.NewDoctorDialog;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static course.academy.entities.enums.Status.COMPLETED;

public class DoctorController {
    private Scanner scanner = new Scanner(System.in);
    private DoctorService doctorService;
    private AppointmentService appointmentService;
    private ClientService clientService;
    private ExaminationService examinationService;
    private PetPassportService passportService;

    public DoctorController(DoctorService doctorService, AppointmentService appointmentService, ClientService clientService, ExaminationService examinationService, PetPassportService passportService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.clientService = clientService;
        this.examinationService = examinationService;
        this.passportService = passportService;
    }

    public void init(Doctor loggedDoctor) {
        Menu menu = new Menu("Doctor Menu", List.of(
                new Menu.Option("Load Data", () -> {
                   doctorService.loadData();
                   clientService.loadData();
                   appointmentService.loadData();
                   examinationService.loadData();
                   return "Information loaded successfully.";
                }),
                new Menu.Option("Update my information", () -> {
                    if (loggedDoctor != null) {
                        Doctor doctor = new NewDoctorDialog().input();
                        loggedDoctor.setFirstName(doctor.getFirstName());
                        loggedDoctor.setLastName(doctor.getLastName());
                        loggedDoctor.setEmail(doctor.getEmail());
                        loggedDoctor.setTelNumber(doctor.getTelNumber());
                        loggedDoctor.setUsername(doctor.getUsername());
                        loggedDoctor.setPassword(loggedDoctor.getPassword());
                        try {
                            doctorService.updateDoctor(loggedDoctor);
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    return "You successfully update your information.";
                }),
                new Menu.Option("View my appointments", () -> {
                    appointmentService.findAll().stream()
                            .filter(a -> a.getChosenDoctorId().equals(loggedDoctor.getId()))
                            .forEach(System.out::println);
                    return "This is your appointments.";
                }),
                new Menu.Option("Complete Appointment", () -> {
                    System.out.println("Appointments:");
                    appointmentService.findAll().stream()
                            .filter(a -> a.getChosenDoctorId().equals(loggedDoctor.getId()))
                            .forEach(System.out::println);
                    System.out.println("Please choose appointment by 'ID':");
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        Appointment appointment = appointmentService.getAppointmentById(id);
                        Client client = clientService.getClientById(appointment.getClientId());
                        PetPassport passport = client.getPet().getPetPassport();
                        if (appointment.getExaminationType().name().equals("DEWORMING")) {
                            passport.setDewormingDate(LocalDate.now());
                            appointment.setStatus(COMPLETED);
                            appointmentService.updateAppointment(appointment);

                        } else if (appointment.getExaminationType().name().equals("VACCINATION")) {
                            passport.setVaccinationDate(LocalDate.now());
                            appointment.setStatus(COMPLETED);
                            appointmentService.updateAppointment(appointment);
                        } else {
                            passport.setExaminationDate(LocalDate.now());
                            System.out.println("Please choose command:");
                            System.out.println("1.Create Examination");
                            System.out.println("2.Exit");
                            int n = Integer.parseInt(scanner.nextLine());
                            if (n == 1) {
                                Examination examination = new NewExaminationDialog().input();
                                Examination createdExamination = doctorService.createExamination(examination);
//                                appointment.setExamination(createdExamination);
                                appointmentService.updateAppointment(appointment);

                                return String.format("Examination ID= '%s' and name '%s' added successfully.",
                                        createdExamination.getId(), createdExamination.getName());
                            } else {
                                appointment.setStatus(COMPLETED);
                            }
                        }
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "Appointment complete successfully.";
                })
        ));
        try {
            menu.show();
        } catch (InvalidEntityDataException | EntityPersistenceException e) {
            e.printStackTrace();
        }
    }
}
