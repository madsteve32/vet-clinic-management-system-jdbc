package course.academy.dao.impl;

import course.academy.dao.*;
import course.academy.service.*;
import course.academy.service.impl.*;
import course.academy.util.AdminValidator;
import course.academy.util.ClientValidator;
import course.academy.util.DoctorValidator;

public class ServiceFactoryImpl implements ServiceFactory {
    private final AdministratorRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;
    private final PetRepository petRepository;
    private final PetPassportRepository passportRepository;
    private final AppointmentRepository appointmentRepository;
    private final ExaminationRepository examinationRepository;

    public ServiceFactoryImpl(AdministratorRepository adminRepository, DoctorRepository doctorRepository, ClientRepository clientRepository, PetRepository petRepository, PetPassportRepository passportRepository, AppointmentRepository appointmentRepository, ExaminationRepository examinationRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.clientRepository = clientRepository;
        this.petRepository = petRepository;
        this.passportRepository = passportRepository;
        this.appointmentRepository = appointmentRepository;
        this.examinationRepository = examinationRepository;
    }

    @Override
    public AdministratorService createAdminService() {
        return new AdministratorServiceImpl(adminRepository, new AdminValidator());
    }

    @Override
    public DoctorService createDoctorService() {
        return new DoctorServiceImpl(doctorRepository, appointmentRepository, examinationRepository, new DoctorValidator());
    }

    @Override
    public ClientService createClientService() {
        return new ClientServiceImpl(clientRepository, appointmentRepository, petRepository, new ClientValidator());
    }

    @Override
    public PetService createPetService() {
        return new PetServiceImpl(petRepository);
    }

    @Override
    public PetPassportService createPassportService() {
        return new PetPassportServiceImpl(passportRepository);
    }

    @Override
    public AppointmentService createAppointmentService() {
        return new AppointmentServiceImpl(appointmentRepository);
    }
}
