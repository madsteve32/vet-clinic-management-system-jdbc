package course.academy.dao;

import course.academy.service.*;

/**
 * public interface for creating new services
 */
public interface ServiceFactory {
    AdministratorService createAdminService();
    DoctorService createDoctorService();
    ClientService createClientService();
    PetService createPetService();
    PetPassportService createPassportService();
    AppointmentService createAppointmentService();
    ExaminationService createExaminationService();
}
