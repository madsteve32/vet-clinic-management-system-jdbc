package course.academy.service.impl;

import course.academy.dao.AppointmentRepository;
import course.academy.dao.DoctorRepository;
import course.academy.dao.ExaminationRepository;
import course.academy.entities.Appointment;
import course.academy.entities.Doctor;
import course.academy.entities.Examination;
import course.academy.exception.ConstraintViolationException;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.DoctorService;
import course.academy.util.DoctorValidator;

import java.time.LocalDate;
import java.util.Collection;

public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final ExaminationRepository examinationRepository;
    private final DoctorValidator doctorValidator;

    public DoctorServiceImpl(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, ExaminationRepository examinationRepository, DoctorValidator doctorValidator) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.examinationRepository = examinationRepository;
        this.doctorValidator = doctorValidator;
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
        try {
            doctorValidator.validate(doctor);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityDataException(
                    String.format("Error creating doctor '%s'", doctor.getUsername()), e);
        }
        Doctor newDoctor = doctorRepository.create(doctor);
        doctorRepository.save();
        return newDoctor;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) throws InvalidEntityDataException, NonexistingEntityException, EntityPersistenceException {
        try {
            doctorValidator.validate(doctor);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityDataException(
                    String.format("Error updating doctor '%s'", doctor.getUsername()), e);
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
