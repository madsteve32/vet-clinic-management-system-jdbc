package course.academy.dao.impl;

import course.academy.dao.*;

import java.sql.Connection;

public class RepositoryFactoryImpl implements RepositoryFactory {
    private Connection connection;

    public RepositoryFactoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AdministratorRepository createAdminRepository() {
        return new AdminRepositoryJdbc(connection);
    }

    @Override
    public DoctorRepository createDoctorRepository() {
        return new DoctorRepositoryJdbc(connection);
    }

    @Override
    public ClientRepository createClientRepository() {
        return new ClientRepositoryJdbc(connection);
    }

    @Override
    public PetRepository createPetRepository() {
        return new PetRepositoryJdbc(connection);
    }

    @Override
    public PetPassportRepository createPassportRepository() {
        return new PetPassportRepositoryJdbc(connection);
    }

    @Override
    public AppointmentRepository createAppointmentRepository() {
        return new AppointmentRepositoryJdbc(connection);
    }

    @Override
    public ExaminationRepository createExaminationRepository() {
        return new ExaminationRepositoryJdbc(connection);
    }
}
