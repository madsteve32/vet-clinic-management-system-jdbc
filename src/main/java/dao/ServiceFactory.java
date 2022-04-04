package dao;

import service.*;

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
}
