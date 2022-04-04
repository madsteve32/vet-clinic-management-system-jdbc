package dao.impl;

import dao.*;
import dao.idGenerator.LongIdGenerator;
import dao.repoFileImpl.*;

public class RepositoryFactoryImpl implements RepositoryFactory {
    @Override
    public AdministratorRepository createAdminRepository() {
        return new AdministratorRepositoryImpl();
    }

    @Override
    public AdministratorRepository createAdminRepositoryFile(String dbFileName) {
        return new AdminRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }

    @Override
    public DoctorRepository createDoctorRepository() {
        return new DoctorRepositoryImpl();
    }

    @Override
    public DoctorRepository createDoctorRepositoryFile(String dbFileName) {
        return new DoctorRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }

    @Override
    public ClientRepository createClientRepository() {
        return new ClientRepositoryImpl();
    }

    @Override
    public ClientRepository createClientRepositoryFile(String dbFileName) {
        return new ClientRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }

    @Override
    public PetRepository createPetRepository() {
        return new PetRepositoryImpl();
    }

    @Override
    public PetRepository createPetRepositoryFile(String dbFileName) {
        return new PetRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }

    @Override
    public PetPassportRepository createPassportRepository() {
        return new PetPassportRepositoryImpl();
    }

    @Override
    public PetPassportRepository createPassportRepositoryFile(String dbFileName) {
        return new PetPassportRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }

    @Override
    public AppointmentRepository createAppointmentRepository() {
        return new AppointmentRepositoryImpl();
    }

    @Override
    public AppointmentRepository createAppointmentRepositoryFile(String dbFileName) {
        return new AppointmentRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }

    @Override
    public ExaminationRepository createExaminationRepository() {
        return new ExaminationRepositoryImpl();
    }

    @Override
    public ExaminationRepository createExaminationRepositoryFile(String dbFileName) {
        return new ExaminationRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }
}
