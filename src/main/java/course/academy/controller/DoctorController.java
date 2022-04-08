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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static course.academy.entities.enums.Status.COMPLETED;

public class DoctorController {
    private Scanner scanner = new Scanner(System.in);
    private DoctorService doctorService;
    private AppointmentService appointmentService;
    private ClientService clientService;
    private PetService petService;
    private ExaminationService examinationService;
    private PetPassportService passportService;

    public DoctorController(DoctorService doctorService, AppointmentService appointmentService, ClientService clientService, PetService petService, ExaminationService examinationService, PetPassportService passportService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.clientService = clientService;
        this.petService = petService;
        this.examinationService = examinationService;
        this.passportService = passportService;
    }

    public void init(Doctor loggedDoctor) {
        Menu menu = new Menu("Doctor Menu", List.of(
                new Menu.Option("Load Data", () -> {
                    List<Appointment> appointments = appointmentService.findAll().stream()
                            .filter(a -> a.getChosenDoctorId().equals(loggedDoctor.getId()))
                            .collect(Collectors.toList());
                   if (loggedDoctor.getAppointments() == null) {
                       loggedDoctor.setAppointments(new ArrayList<>());
                       for (Appointment appointment : appointments) {
                           loggedDoctor.getAppointments().add(appointment);
                       }
                   } else {
                       for (Appointment appointment : appointments) {
                           loggedDoctor.getAppointments().add(appointment);
                       }
                   }
                   return "Data loaded successfully.";
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
                    List<Appointment> appointments = loggedDoctor.getAppointments();
                    if (appointments.size() > 0) {
                        appointments.forEach(System.out::println);
                    } else {
                        return "You don't have any appointments.";
                    }
                    return "This is your appointments.";
                }),
                new Menu.Option("Complete Appointment", () -> {
                    System.out.println("Appointments:");
                    List<Appointment> appointments = loggedDoctor.getAppointments();
                    appointments.forEach(System.out::println);
                    System.out.println("Please choose appointment by 'ID':");
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        Appointment appointment = appointmentService.getAppointmentById(id);
                        Client client = clientService.getClientById(appointment.getClientId());
                        Pet pet = petService.getPetByClientId(client.getId());
                        PetPassport passport = passportService.getPassportByPetId(pet.getId());
                        if (appointment.getExaminationType().name().equals("DEWORMING")) {
                            System.out.println("Please enter next date for deworming in format (dd/MM/yyyy):");
                            String strDate = scanner.nextLine();
                            LocalDate date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("d/MM/yyyy"));
                            passport.setDewormingDate(date);
                            appointment.setStatus(COMPLETED);
                            appointmentService.updateAppointment(appointment);
                            passportService.updatePassportDewormingDate(passport);

                        } else if (appointment.getExaminationType().name().equals("VACCINATION")) {
                            System.out.println("Please enter next date for vaccination in format (dd/MM/yyyy):");
                            String strDate = scanner.nextLine();
                            LocalDate date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("d/MM/yyyy"));
                            passport.setVaccinationDate(date);
                            appointment.setStatus(COMPLETED);
                            appointmentService.updateAppointment(appointment);
                            passportService.updatePassportVaccinationDate(passport);
                        } else {
                            passport.setExaminationDate(LocalDate.now());
                            passportService.updatePassportExaminationDate(passport);
                            System.out.println("Please choose command:");
                            System.out.println("1.Create Examination");
                            System.out.println("2.Exit");
                            int n = Integer.parseInt(scanner.nextLine());
                            if (n == 1) {
                                Examination examination = new NewExaminationDialog().input();
                                Examination createdExamination = examinationService.addExamination(examination);
                                appointment.setExaminationId(examination.getId());
                                appointmentService.updateAppointmentExamination(appointment);

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
