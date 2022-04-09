package course.academy.controller;

import course.academy.entities.Administrator;
import course.academy.entities.Appointment;
import course.academy.entities.Client;
import course.academy.entities.Doctor;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.AdministratorService;
import course.academy.service.AppointmentService;
import course.academy.service.ClientService;
import course.academy.service.DoctorService;
import course.academy.view.Menu;
import course.academy.view.newUserDialogs.NewAdminDialog;
import course.academy.view.newUserDialogs.NewClientDialog;
import course.academy.view.newUserDialogs.NewDoctorDialog;
import course.academy.view.updateUserDialogs.UpdateAdminDialog;
import course.academy.view.updateUserDialogs.UpdateClientDialog;
import course.academy.view.updateUserDialogs.UpdateDoctorDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static course.academy.entities.enums.Status.COMPLETED;

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
                        Administrator admin = new NewAdminDialog().input();
                        updatedAdmin.setFirstName(admin.getFirstName());
                        updatedAdmin.setLastName(admin.getLastName());
                        updatedAdmin.setEmail(admin.getEmail());
                        updatedAdmin.setTelNumber(admin.getTelNumber());
                        updatedAdmin.setUsername(admin.getUsername());
                        updatedAdmin.setPassword(admin.getPassword());
                        try {
                            adminService.updateAdmin(updatedAdmin);
                            return String.format("Admin with ID='%s' and username='%s' was updated successfully.",
                                    updatedAdmin.getId(), updatedAdmin.getUsername());
                        } catch (NonexistingEntityException e) {
                            e.printStackTrace();
                        }
                    } else if (updatedDoctor != null) {
                        Doctor doctor = new NewDoctorDialog().input();
                        updatedDoctor.setFirstName(doctor.getFirstName());
                        updatedDoctor.setLastName(doctor.getLastName());
                        updatedDoctor.setEmail(doctor.getEmail());
                        updatedDoctor.setTelNumber(doctor.getTelNumber());
                        updatedDoctor.setUsername(doctor.getUsername());
                        updatedDoctor.setPassword(doctor.getPassword());
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
                        updatedClient.setEmail(client.getEmail());
                        updatedClient.setTelNumber(client.getTelNumber());
                        updatedClient.setUsername(client.getUsername());
                        updatedClient.setPassword(client.getPassword());
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
                new Menu.Option("Delete Appointment", () -> {
                    System.out.println("Appointments");
                    appointmentService.findAll().forEach(System.out::println);
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
