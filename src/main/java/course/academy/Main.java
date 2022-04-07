package course.academy;

import course.academy.controller.UserController;
import course.academy.dao.*;
import course.academy.dao.impl.*;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.jdbc.JdbcSimpleDemo;
import course.academy.service.*;
import course.academy.service.impl.*;
import course.academy.util.AdminValidator;
import course.academy.util.ClientValidator;
import course.academy.util.DoctorValidator;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static course.academy.util.JdbcUtils.closeConnection;
import static course.academy.util.JdbcUtils.createDbConnection;

@Slf4j
public class Main {
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
        AppointmentRepository appointmentRepository = new AppointmentRepositoryJdbc(connection);
        ExaminationRepository examinationRepository = new ExaminationRepositoryImpl();

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
