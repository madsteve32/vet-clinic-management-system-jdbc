package course.academy.dao;

/**
 * public interface for creating new repositories
 */
public interface RepositoryFactory {
    AdministratorRepository createAdminRepository();

    DoctorRepository createDoctorRepository();

    ClientRepository createClientRepository();

    PetRepository createPetRepository();

    PetPassportRepository createPassportRepository();

    AppointmentRepository createAppointmentRepository();

    ExaminationRepository createExaminationRepository();
}
