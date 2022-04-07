package course.academy.dao.impl;

import course.academy.dao.*;

public class RepositoryFactoryImpl implements RepositoryFactory {
    @Override
    public AdministratorRepository createAdminRepository() {
        return new AdministratorRepositoryImpl();
    }

    @Override
    public DoctorRepository createDoctorRepository() {
        return new DoctorRepositoryImpl();
    }

    @Override
    public ClientRepository createClientRepository() {
        return new ClientRepositoryImpl();
    }

    @Override
    public PetRepository createPetRepository() {
        return new PetRepositoryImpl();
    }

    @Override
    public PetPassportRepository createPassportRepository() {
        return new PetPassportRepositoryImpl();
    }

    @Override
    public AppointmentRepository createAppointmentRepository() {
        return new AppointmentRepositoryImpl();
    }

    @Override
    public ExaminationRepository createExaminationRepository() {
        return new ExaminationRepositoryImpl();
    }
}
