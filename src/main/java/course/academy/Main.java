package course.academy;

import course.academy.controller.UserController;
import course.academy.dao.*;
import course.academy.dao.impl.*;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.*;
import course.academy.service.impl.*;
import course.academy.util.AdminValidator;
import course.academy.util.ClientValidator;
import course.academy.util.DoctorValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static course.academy.util.JdbcUtils.closeConnection;
import static course.academy.util.JdbcUtils.createDbConnection;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, EntityPersistenceException {
        Properties props = new Properties();
        String dbConfigPath = Main.class.getClassLoader()
                .getResource("jdbc.properties").getPath();
        props.load(new FileInputStream(dbConfigPath));

        //1. Load DB driver
        Connection connection = createDbConnection(props);

        RepositoryFactory repositoryFactory = new RepositoryFactoryImpl(connection);
        AdministratorRepository adminRepository = repositoryFactory.createAdminRepository();
        DoctorRepository doctorRepository = repositoryFactory.createDoctorRepository();
        ClientRepository clientRepository = repositoryFactory.createClientRepository();
        PetRepository petRepository = repositoryFactory.createPetRepository();
        PetPassportRepository passportRepository = repositoryFactory.createPassportRepository();
        AppointmentRepository appointmentRepository = repositoryFactory.createAppointmentRepository();
        ExaminationRepository examinationRepository = repositoryFactory.createExaminationRepository();

        AdministratorService adminService = new AdministratorServiceImpl(adminRepository, new AdminValidator());
        DoctorService doctorService = new DoctorServiceImpl(doctorRepository, appointmentRepository, examinationRepository, new DoctorValidator());
        ClientService clientService = new ClientServiceImpl(clientRepository, appointmentRepository, petRepository, new ClientValidator());
        PetService petService = new PetServiceImpl(petRepository);
        PetPassportService passportService = new PetPassportServiceImpl(passportRepository);
        AppointmentService appointmentService = new AppointmentServiceImpl(appointmentRepository);
        ExaminationService examinationService = new ExaminationServiceImpl(examinationRepository);
//        try {
//            adminService.addAdmin(new Administrator("Steven", "Lyutov", "steven@test.com", "0898887766",
//                    "admin", "Admin_123", MALE, ADMIN));
//        } catch (InvalidEntityDataException e) {
//            System.out.println(e.getMessage());
//        }

        UserController userController = new UserController(adminService, doctorService, clientService, petService, passportService, appointmentService, examinationService);
        try {
            userController.login();
        } catch (NonexistingEntityException | InvalidEntityDataException e) {
            e.printStackTrace();
        }

        closeConnection(connection);
    }
}
