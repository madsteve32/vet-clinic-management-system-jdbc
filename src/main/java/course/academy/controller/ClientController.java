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

import static course.academy.entities.enums.Status.COMPLETED;

public class ClientController {
    private Scanner scanner = new Scanner(System.in);
    private ClientService clientService;
    private PetService petService;
    private PetPassportService passportService;
    private AppointmentService appointmentService;
    private DoctorService doctorService;

    public ClientController(ClientService clientService, PetService petService, PetPassportService passportService, AppointmentService appointmentService, DoctorService doctorService) {
        this.clientService = clientService;
        this.petService = petService;
        this.passportService = passportService;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    public void init(Client loggedClient) {
        Menu menu = new Menu("Client Menu", List.of(
                new Menu.Option("Load Data", () -> {
                    if (loggedClient.getPet() == null) {
                        try {
                            Pet pet = petService.getPetByClientId(loggedClient.getId());
                            loggedClient.setPet(pet);
                            if (pet.getPetPassport() == null) {
                                PetPassport passport = passportService.getPassportByPetId(pet.getId());
                                pet.setPetPassport(passport);
                            }
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
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
                    Pet createdPet = petService.addPet(pet);
                    loggedClient.setPet(createdPet);
                    try {
                        clientService.updateClient(loggedClient);
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return String.format("Pet with name= %s and breed= %s with was added successfully.",
                            createdPet.getName(), createdPet.getBreed());
                }),
                new Menu.Option("Add Pet Passport", () -> {
                    PetPassport passport = new PetPassport();
                    passport.setExaminationDate(LocalDate.now());
                    passport.setVaccinationDate(LocalDate.now());
                    passport.setDewormingDate(LocalDate.now());
                    passport.setPetId(loggedClient.getPet().getId());
                    passport.setClientId(loggedClient.getId());
                    PetPassport newPassport = passportService.addPassport(passport);
                    return String.format("Passport with id= %s and petId= %s was added successfully.",
                            newPassport.getId(), newPassport.getPetId());
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
                        if (chosenDoctor.getAppointments().size() < 10) {
                            appointment.setChosenDoctor(chosenDoctor);
                        } else {
                            System.out.println("Sorry doctor appointments is full chose another doctor.");
                            id = Long.parseLong(scanner.nextLine());
                        }
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    Appointment newAppointment = appointmentService.addAppointment(appointment);
                    loggedClient.getAppointments().add(newAppointment);
                    try {
                        clientService.updateClient(loggedClient);
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return String.format("Appointment with id= %s and Doctor name= %s was added successful;y.",
                            newAppointment.getId(), newAppointment.getChosenDoctor().getFirstName());
                }),
                new Menu.Option("View my appointments", () -> {
                    Collection<Appointment> appointments = appointmentService.findAll();
                    if (appointments.size() > 0) {
                        appointments.stream()
                                .filter(a -> a.getClientId().equals(loggedClient.getId()))
                                .forEach(System.out::println);
                    } else {
                        return "You don't have any appointments";
                    }
                    return "Total appointments count: " + appointments.size();
                }),
                new Menu.Option("View my pet passport", () -> {
                    PetPassport passport = loggedClient.getPet().getPetPassport();
                    return "This is your passport: " + passport;
                }),
                new Menu.Option("Complete Examination", () -> {
                    System.out.println("Please choose appointment by ID");
                    appointmentService.findAll().stream()
                            .filter(a -> a.getExamination() != null)
                            .forEach(System.out::println);
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        Appointment appointment = appointmentService.getAppointmentById(id);
                        Examination examination = appointment.getExamination();
                        examination.setStatus(COMPLETED);
                        appointment.setStatus(COMPLETED);
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "You successfully complete your pet examination.";
                }),
                new Menu.Option("Check completed appointments", () -> {
                    appointmentService.findAll().stream()
                            .filter(a -> a.getStatus().name().equals("COMPLETED"))
                            .forEach(System.out::println);
                    System.out.println("Please choose appointment by ID to be deleted:");
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        appointmentService.deleteAppointmentById(id);
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
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
