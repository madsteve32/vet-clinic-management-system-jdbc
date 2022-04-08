package course.academy.controller;

import course.academy.entities.Administrator;
import course.academy.entities.Client;
import course.academy.entities.Doctor;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.*;

import java.util.Scanner;

public class UserController {
    public Scanner scanner = new Scanner(System.in);
    private final AdministratorService adminService;
    private final DoctorService doctorService;
    private final ClientService clientService;
    private final PetService petService;
    private final PetPassportService passportService;
    private final AppointmentService appointmentService;
    private final ExaminationService examinationService;

    public UserController(AdministratorService adminService, DoctorService doctorService, ClientService clientService, PetService petService, PetPassportService passportService, AppointmentService appointmentService, ExaminationService examinationService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.clientService = clientService;
        this.petService = petService;
        this.passportService = passportService;
        this.appointmentService = appointmentService;
        this.examinationService = examinationService;
    }

    Administrator loggedAdmin = null;
    Doctor loggedDoctor = null;
    Client loggedClient = null;
    boolean isLogged = true;
    public void login() throws NonexistingEntityException, InvalidEntityDataException, EntityPersistenceException {
        adminService.loadData();
        doctorService.loadData();
        clientService.loadData();
        petService.loadData();
        passportService.loadData();
        appointmentService.loadData();
        examinationService.loadData();

        System.out.println("Welcome to Vet clinic management system(VCMS):");
        System.out.println("1.Login as Admin");
        System.out.println("2.Login as Doctor");
        System.out.println("3.Login as Client");
        int n = Integer.parseInt(scanner.nextLine());
        String username = "";
        String password = "";
        if (n == 1) {
            System.out.println("Please enter your username:");
            username = scanner.nextLine();
            loggedAdmin = adminService.findByUsername(username);
            while (loggedAdmin == null) {
                System.out.println("Wrong username please try again:");
                username = scanner.nextLine();
                loggedAdmin = adminService.findByUsername(username);
            }
            System.out.println("Please enter your password:");
            password = scanner.nextLine();
            while (!loggedAdmin.getPassword().equals(password)) {
                System.out.println("Wrong password please try again");
                password = scanner.nextLine();
            }
        } else if (n == 2) {
            System.out.println("Please enter your username:");
            username = scanner.nextLine();
            loggedDoctor = doctorService.findByUsername(username);
            while (loggedDoctor == null) {
                System.out.println("Wrong username please try again:");
                username = scanner.nextLine();
                loggedDoctor = doctorService.findByUsername(username);
            }
            System.out.println("Please enter your password:");
            password = scanner.nextLine();
            while (!loggedDoctor.getPassword().equals(password)) {
                System.out.println("Wrong password please try again");
                password = scanner.nextLine();
            }
        } else {
            System.out.println("Please enter your username:");
            username = scanner.nextLine();
            loggedClient = clientService.findByUsername(username);
            while (loggedClient == null) {
                System.out.println("Wrong username please try again:");
                username = scanner.nextLine();
                loggedClient = clientService.findByUsername(username);
            }
            System.out.println("Please enter your password:");
            password = scanner.nextLine();
            while (!loggedClient.getPassword().equals(password)) {
                System.out.println("Wrong password please try again");
                password = scanner.nextLine();
            }
        }
        if (loggedAdmin != null) {
            AdminController adminController = new AdminController(adminService, doctorService, clientService, appointmentService);
            adminController.init();
        } else if (loggedDoctor != null) {
            DoctorController doctorController = new DoctorController(doctorService, appointmentService, clientService, petService, examinationService, passportService);
            doctorController.init(loggedDoctor);
        } else {
            ClientController clientController = new ClientController(clientService, petService, passportService, appointmentService, doctorService);
            clientController.init(loggedClient);
        }
    }
}
