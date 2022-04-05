package course.academy.service.impl;

import course.academy.dao.AppointmentRepository;
import course.academy.dao.DoctorRepository;
import course.academy.dao.ExaminationRepository;
import course.academy.entities.Appointment;
import course.academy.entities.Doctor;
import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.DoctorService;

import java.time.LocalDate;
import java.util.Collection;

public class DoctorServiceImpl implements DoctorService {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{8,20})";
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final ExaminationRepository examinationRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, ExaminationRepository examinationRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.examinationRepository = examinationRepository;
    }

    @Override
    public void loadData() {
        doctorRepository.load();
    }

    @Override
    public void saveData() {
        doctorRepository.save();
    }

    @Override
    public Collection<Doctor> findAll() throws EntityPersistenceException {
        return doctorRepository.findAll();
    }

    @Override
    public Appointment updateAppointmentInfo(Appointment appointment) throws NonexistingEntityException, EntityPersistenceException {
        Appointment updatedAppointment = appointmentRepository.update(appointment);
        appointmentRepository.save();
        return updatedAppointment;
    }

    @Override
    public Examination createExamination(Examination examination) throws InvalidEntityDataException, EntityPersistenceException {
        if (examination.getDate().isBefore(LocalDate.now())) {
            throw new InvalidEntityDataException("Examination date cannot be in the past.");
        }
        Examination newExamination = examinationRepository.create(examination);
        examinationRepository.save();
        return newExamination;
    }

    @Override
    public Doctor getDoctorById(Long id) throws NonexistingEntityException {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor addDoctor(Doctor doctor) throws InvalidEntityDataException, EntityPersistenceException {
        int firstNameLength = doctor.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }

        int lastNameLength = doctor.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }


        if (!doctor.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidEntityDataException("Email must be valid.");
        }

        int usernameLength = doctor.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }

        if (!doctor.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidEntityDataException("Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit");
        }
        Doctor newDoctor = doctorRepository.create(doctor);
        doctorRepository.save();
        return newDoctor;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) throws InvalidEntityDataException, NonexistingEntityException, EntityPersistenceException {
        int firstNameLength = doctor.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }

        int lastNameLength = doctor.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }


        if (!doctor.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidEntityDataException("Email must be valid.");
        }

        int usernameLength = doctor.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }

        if (!doctor.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidEntityDataException("Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit");
        }
        Doctor updatedDoctor = doctorRepository.update(doctor);
        doctorRepository.save();
        return updatedDoctor;
    }

    @Override
    public Doctor deleteDoctorById(Long id) throws NonexistingEntityException {
        Doctor deletedDoctor = doctorRepository.deleteById(id);
        doctorRepository.save();
        return deletedDoctor;
    }

    @Override
    public Doctor findByUsername(String username) throws EntityPersistenceException {
        Collection<Doctor> allDoctors = doctorRepository.findAll();
        for (Doctor doctor : allDoctors) {
            if (doctor.getUsername().equals(username)) {
                return doctor;
            }
        }
        return null;
    }

    @Override
    public long count() {
        return doctorRepository.count();
    }
}
