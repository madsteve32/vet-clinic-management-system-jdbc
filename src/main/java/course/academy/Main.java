package course.academy;

import course.academy.controller.UserController;
import course.academy.dao.*;
import course.academy.dao.impl.*;
import course.academy.entities.Administrator;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.jdbc.JdbcSimpleDemo;
import course.academy.service.*;
import course.academy.service.impl.*;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static course.academy.entities.enums.Gender.MALE;
import static course.academy.entities.enums.Role.ADMIN;

@Slf4j
public class Main {
    public static Connection createDbConnection(Properties props) throws ClassNotFoundException, SQLException {
        // 1. Load DB Driver
        try {
            Class.forName(props.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw e;
        }
        // 2. Create DB Connection and 3.Create Statement
        return DriverManager.getConnection(props.getProperty("url"), props);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, EntityPersistenceException {
        Properties props = new Properties();
        String dbConfigPath = JdbcSimpleDemo.class.getClassLoader()
                .getResource("jdbc.properties").getPath();
        props.load(new FileInputStream(dbConfigPath));

        //1. Load DB driver
        Connection connection = createDbConnection(props);

        AdministratorRepository adminRepository = new AdminRepositoryJdbc(connection);
        DoctorRepository doctorRepository = new DoctorRepositoryJdbc(connection);
        ClientRepository clientRepository = new ClientRepositoryJdbc(connection);
        PetRepository petRepository = new PetRepositoryJdbc(connection);
        PetPassportRepository passportRepository = new PetPassportRepositoryJdbc(connection);
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        ExaminationRepository examinationRepository = new ExaminationRepositoryImpl();

        AdministratorService adminService = new AdministratorServiceImpl(adminRepository);
        DoctorService doctorService = new DoctorServiceImpl(doctorRepository, appointmentRepository, examinationRepository);
        ClientService clientService = new ClientServiceImpl(clientRepository, appointmentRepository, petRepository);
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
