package controller;

import entities.Administrator;
import entities.Appointment;
import entities.Client;
import entities.Doctor;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;
import service.AdministratorService;
import service.AppointmentService;
import service.ClientService;
import service.DoctorService;
import view.Menu;
import view.newUserDialogs.NewAdminDialog;
import view.newUserDialogs.NewClientDialog;
import view.newUserDialogs.NewDoctorDialog;
import view.updateUserDialogs.UpdateAdminDialog;
import view.updateUserDialogs.UpdateClientDialog;
import view.updateUserDialogs.UpdateDoctorDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static entities.enums.Status.COMPLETED;

public class AdminController {
    private AdministratorService adminService;
    private DoctorService doctorService;
    private ClientService clientService;
    private AppointmentService appointmentService;
    private Scanner scanner = new Scanner(System.in);

    public AdminController(AdministratorService adminService, DoctorService doctorService, ClientService clientService, AppointmentService appointmentService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.clientService = clientService;
        this.appointmentService = appointmentService;
    }

    public void init() {
        Menu menu = new Menu("Admin Menu", List.of(
                new Menu.Option("Load Users", () -> {
                    System.out.println("Loading Users...");
                    adminService.loadData();
                    doctorService.loadData();
                    clientService.loadData();
                    return "Users loaded successfully.";
                }),
                new Menu.Option("Show all Users", () -> {
                    System.out.println("Administrators:");
                    adminService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Doctors:");
                    doctorService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Clients:");
                    clientService.findAll().forEach(System.out::println);
                    System.out.println();
                    int totalUsersCount = adminService.findAll().size() + doctorService.findAll().size()
                            + clientService.findAll().size();
                    return "Total users count: " + totalUsersCount;
                }),
                new Menu.Option("Add new User", () -> {
                    System.out.println("Please choose user type:");
                    System.out.println("1.Admin user");
                    System.out.println("2.Doctor user");
                    System.out.println("3.Client User");
                    String answer = scanner.nextLine();
                    if (answer.equals("1")) {
                        Administrator administrator = new NewAdminDialog().input();
                        Administrator createdAdmin = adminService.addAdmin(administrator);
                        return String.format("Admin ID= '%s' and name '%s' added successfully.",
                                createdAdmin.getId(), createdAdmin.getFirstName());
                    } else if (answer.equals("2")) {
                        Doctor doctor = new NewDoctorDialog().input();
                        doctor.setAppointments(new ArrayList<>());
                        Doctor createdDoctor = doctorService.addDoctor(doctor);
                        return String.format("Doctor ID= '%s' and name '%s' added successfully.",
                                createdDoctor.getId(), createdDoctor.getFirstName());
                    } else {
                        Client client = new NewClientDialog().input();
                        client.setAppointments(new ArrayList<>());
                        Client createdClient = clientService.addClient(client);
                        return String.format("Client ID= '%s' and name '%s' added successfully.",
                                createdClient.getId(), createdClient.getFirstName());
                    }
                }),
                new Menu.Option("Update User", () -> {
                    System.out.println("Please choose user by username:");
                    System.out.println("Administrators:");
                    adminService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Doctors:");
                    doctorService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Clients:");
                    clientService.findAll().forEach(System.out::println);
                    String username = scanner.nextLine();
                    Administrator updatedAdmin = adminService.findByUsername(username);
                    Doctor updatedDoctor = doctorService.findByUsername(username);
                    Client updatedClient = clientService.findByUsername(username);
                    if (updatedAdmin != null) {
                        Administrator admin = new UpdateAdminDialog().input();
                        updatedAdmin.setFirstName(admin.getFirstName());
                        updatedAdmin.setLastName(admin.getLastName());
                        updatedAdmin.setTelNumber(admin.getTelNumber());
                        updatedAdmin.setUsername(admin.getUsername());
                        try {
                            adminService.updateAdmin(updatedAdmin);
                            return String.format("Admin with ID='%s' and username='%s' was updated successfully.",
                                    updatedAdmin.getId(), updatedAdmin.getUsername());
                        } catch (NonexistingEntityException e) {
                            e.printStackTrace();
                        }
                    } else if (updatedDoctor != null) {
                        Doctor doctor = new UpdateDoctorDialog().input();
                        updatedDoctor.setFirstName(doctor.getFirstName());
                        updatedDoctor.setLastName(doctor.getLastName());
                        updatedDoctor.setTelNumber(doctor.getTelNumber());
                        updatedDoctor.setUsername(doctor.getUsername());
                        try {
                            doctorService.updateDoctor(updatedDoctor);
                            return String.format("Doctor with ID='%s' and username='%s' was updated successfully.",
                                    updatedDoctor.getId(), updatedDoctor.getUsername());
                        } catch (NonexistingEntityException e) {
                            System.out.println("Doctor " + e.getMessage());
                        }
                    } else {
                        Client client = new UpdateClientDialog().input();
                        updatedClient.setFirstName(client.getFirstName());
                        updatedClient.setLastName(client.getLastName());
                        updatedClient.setTelNumber(client.getTelNumber());
                        updatedClient.setUsername(client.getUsername());
                        try {
                            clientService.updateClient(updatedClient);
                            return String.format("Client with ID='%s' and username='%s' was updated successfully.",
                                    updatedClient.getId(), updatedClient.getUsername());
                        } catch (NonexistingEntityException e) {
                            e.printStackTrace();
                        }
                    }
                    return "User updated successfully.";
                }),
                new Menu.Option("Delete user", () -> {
                    System.out.println("Please choose user by username:");
                    System.out.println("Administrators:");
                    adminService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Doctors:");
                    doctorService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Clients:");
                    clientService.findAll().forEach(System.out::println);
                    String username = scanner.nextLine();
                    Administrator deletedAdmin = adminService.findByUsername(username);
                    Doctor deletedDoctor = doctorService.findByUsername(username);
                    Client deletedClient = clientService.findByUsername(username);
                    if (deletedAdmin != null) {
                        try {
                            adminService.deleteAdminById(deletedAdmin.getId());
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (deletedDoctor != null) {
                        try {
                            doctorService.deleteDoctorById(deletedDoctor.getId());
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        try {
                            clientService.deleteClientById(deletedClient.getId());
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    return "You successfully delete user.";
                }),
                new Menu.Option("View all Appointments", () -> {
                    System.out.println("Appointments:");
                    appointmentService.loadData();
                    appointmentService.findAll().forEach(System.out::println);
                    return "Appointments total count is: " + appointmentService.findAll().size();
                }),
                new Menu.Option("Complete Appointment", () -> {
                    appointmentService.loadData();
                    System.out.println("Appointments:");
                    appointmentService.findAll().forEach(System.out::println);
                    System.out.println();
                    System.out.println("Please choose appointment by ID:");
                    long id = Long.parseLong(scanner.nextLine());
                    try {
                        Appointment appointment = appointmentService.getAppointmentById(id);
                        appointment.setStatus(COMPLETED);
                        appointmentService.updateAppointment(appointment);
                    } catch (NonexistingEntityException e) {
                        e.printStackTrace();
                    }
                    return "Appointment completed";
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
        } catch (InvalidEntityDataException e) {
            e.printStackTrace();
        }
    }
}
