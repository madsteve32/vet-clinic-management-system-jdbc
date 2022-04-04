package dao;

/**
 * public interface for creating new repositories
 */
public interface RepositoryFactory {
    AdministratorRepository createAdminRepository();
    AdministratorRepository createAdminRepositoryFile(String dbFileName);

    DoctorRepository createDoctorRepository();
    DoctorRepository createDoctorRepositoryFile(String dbFileName);

    ClientRepository createClientRepository();
    ClientRepository createClientRepositoryFile(String dbFileName);

    PetRepository createPetRepository();
    PetRepository createPetRepositoryFile(String dbFileName);
    
    PetPassportRepository createPassportRepository();
    PetPassportRepository createPassportRepositoryFile(String dbFileName);

    AppointmentRepository createAppointmentRepository();
    AppointmentRepository createAppointmentRepositoryFile(String dbFileName);

    ExaminationRepository createExaminationRepository();
    ExaminationRepository createExaminationRepositoryFile(String dbFileName);
}
