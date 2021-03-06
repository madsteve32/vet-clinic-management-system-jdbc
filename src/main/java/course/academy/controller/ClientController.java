package course.academy.controller;

import course.academy.entities.*;
import course.academy.entities.enums.Status;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.*;
import course.academy.view.Menu;
import course.academy.view.NewAppointmentDialog;
import course.academy.view.NewPetDialog;
import course.academy.view.newUserDialogs.NewClientDialog;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static course.academy.entities.enums.Status.COMPLETED;

public class ClientController {
    private Scanner scanner = new Scanner(System.in);
    private ClientService clientService;
    private PetService petService;
    private PetPassportService passportService;
    private AppointmentService appointmentService;
    private ExaminationService examinationService;
    private DoctorService doctorService;

    public ClientController(ClientService clientService, PetService petService, PetPassportService passportService, AppointmentService appointmentService, ExaminationService examinationService, DoctorService doctorService) {
        this.clientService = clientService;
        this.petService = petService;
        this.passportService = passportService;
        this.appointmentService = appointmentService;
        this.examinationService = examinationService;
        this.doctorService = doctorService;
    }

    public void init(Client loggedClient) {
        Menu menu = new Menu("Client Menu", List.of(
                new Menu.Option("Load Data", () -> {
                    if (loggedClient.getPet() == null) {
                        Pet pet = null;
                        try {
                            pet = petService.getPetByClientId(loggedClient.getId());
                            loggedClient.setPet(pet);
                        } catch (NonexistingEntityException e) {
                            return "You don't have created pet.";
                        }
                        if (pet.getPetPassport() == null) {
                            PetPassport passport = null;
                            try {
                                passport = passportService.getPassportByPetId(pet.getId());
                                pet.setPetPassport(passport);
                            } catch (NonexistingEntityException e) {
                                return "You don't have passport for your pet.";
                            }
                        }
                    }
                    return "Data loaded successfully.";
                }),
                new Menu.Option("Update my information", () -> {
                    Client client = new NewClientDialog().input();
                    loggedClient.setFirstName(client.getFirstName());
                    loggedClient.setLastName(client.getLastName());
                    loggedClient.setEmail(client.getEmail());
                    loggedClient.setTelNumber(client.getTelNumber());
                    loggedClient.setUsername(client.getUsername());
                    loggedClient.setPassword(client.getPassword());
                    try {
                        clientService.updateClient(loggedClient);
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "You successfully update your information.";
                }),
                new Menu.Option("Add pet", () -> {
                    Pet pet = new NewPetDialog().input();
                    pet.setClientId(loggedClient.getId());
                    Pet createdPet = petService.addPet(pet);
                    loggedClient.setPet(createdPet);
                    return String.format("Pet with name= %s and breed= %s was added successfully.",
                            createdPet.getName(), createdPet.getBreed());
                }),
                new Menu.Option("Add Pet Passport", () -> {
                    try {
                        Pet pet = petService.getPetByClientId(loggedClient.getId());
                        PetPassport passport = new PetPassport();
                        passport.setExaminationDate(LocalDate.now());
                        passport.setVaccinationDate(LocalDate.now());
                        passport.setDewormingDate(LocalDate.now());
                        passport.setPetId(pet.getId());
                        passport.setClientId(loggedClient.getId());
                        passportService.addPassport(passport);
                        String.format("Passport with ID= '%s' for Pet with name '%s' was added successful.%n", passport.getId(), pet.getName());
                    } catch (NonexistingEntityException e) {
                        e.printStackTrace();
                    }
                    return "Pet passport added successfully.";
                }),
                new Menu.Option("Create Appointment", () -> {
                    Appointment appointment = new NewAppointmentDialog().input();
                    appointment.setClientId(loggedClient.getId());
                    appointment.setStatus(Status.WAITING);
                    System.out.println("Please chose doctor by ID:");
                    doctorService.findAll().forEach(System.out::println);
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        Doctor chosenDoctor = doctorService.getDoctorById(id);
                        appointment.setChosenDoctorId(chosenDoctor.getId());
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    Appointment newAppointment = appointmentService.addAppointment(appointment);
                    return String.format("Appointment with id= %s and Doctor id= %s was added successful;y.",
                            newAppointment.getId(), newAppointment.getChosenDoctorId());
                }),
                new Menu.Option("View my appointments", () -> {
                    List<Appointment> appointments = appointmentService.findAll().stream()
                            .filter(a -> a.getClientId().equals(loggedClient.getId()))
                            .collect(Collectors.toList());
                    if (appointments.size() > 0) {
                        appointments.forEach(System.out::println);
                    } else {
                        return "You don't have any appointments";
                    }
                    return "Total appointments count: " + appointments.size();
                }),
                new Menu.Option("Delete my Appointment", () -> {
                    System.out.println("Appointments");
                    List<Appointment> appointments = appointmentService.findAll().stream()
                            .filter(a -> a.getClientId().equals(loggedClient.getId()))
                            .collect(Collectors.toList());
                    if (appointments.size() > 0) {
                        appointments.forEach(System.out::println);
                    } else {
                        return "You don't have any appointments";
                    }
                    System.out.println("Please chose appointment by ID to be deleted:");
                    long id = Long.parseLong(scanner.nextLine());
                    Appointment deletedAppointment = null;
                    try {
                        deletedAppointment = appointmentService.deleteAppointmentById(id);
                        return String.format("You successful delete appointment with ID= %s", deletedAppointment.getId());
                    } catch (NonexistingEntityException e) {
                        return "Appointment with ID= " + id + "does not exist.";
                    }
                }),
                new Menu.Option("View my pet passport", () -> {
                    PetPassport passport = loggedClient.getPet().getPetPassport();
                    return "This is your passport: " + passport;
                }),
                new Menu.Option("Complete Examination", () -> {
                    System.out.println("Please choose appointment by ID");
                    appointmentService.findAll().stream()
                            .filter(a -> a.getExaminationId() > 0 && a.getClientId().equals(loggedClient.getId()))
                            .forEach(System.out::println);
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        Appointment appointment = appointmentService.getAppointmentById(id);
                        Examination examination = examinationService.getExaminationById(appointment.getExaminationId());
                        examination.setStatus(COMPLETED);
                        examinationService.updateExamination(examination);
                        appointment.setStatus(COMPLETED);
                        appointmentService.updateAppointment(appointment);
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "You successfully complete your pet examination.";
                }),
                new Menu.Option("Check completed appointments", () -> {
                    Collection<Appointment> appointments = appointmentService.findAll().stream()
                            .filter(a -> a.getStatus().name().equals("COMPLETED"))
                            .collect(Collectors.toList());
                    if (appointments.size() > 0) {
                        appointments.forEach(System.out::println);
                        System.out.println("Please choose appointment by ID to be deleted:");
                        long id = Long.parseLong(scanner.nextLine());
                        try {
                            Appointment deletedAppointment = appointmentService.deleteAppointmentById(id);
                            Examination examination = examinationService.getExaminationById(deletedAppointment.getExaminationId());
                            examinationService.deleteExaminationById(examination.getId());
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        return "You don't have any completed appointments.";
                    }
                    return "You successfully delete completed appointment.";
                })
        ));
        try {
            menu.show();
        } catch (InvalidEntityDataException | EntityPersistenceException e) {
            e.printStackTrace();
        }
    }
}
