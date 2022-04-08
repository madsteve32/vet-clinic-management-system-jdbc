package course.academy.service;

import course.academy.entities.Appointment;
import course.academy.entities.Doctor;
import course.academy.entities.Examination;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;

public interface DoctorService {
    void loadData();
    void saveData();
    Collection<Doctor> findAll() throws EntityPersistenceException;
    Appointment updateAppointmentInfo(Appointment appointment) throws NonexistingEntityException, EntityPersistenceException;
    Examination createExamination(Examination examination) throws InvalidEntityDataException, EntityPersistenceException;
    Doctor getDoctorById(Long id) throws NonexistingEntityException;
    Doctor addDoctor(Doctor doctor) throws InvalidEntityDataException, EntityPersistenceException;
    Doctor updateDoctor(Doctor doctor) throws InvalidEntityDataException, NonexistingEntityException, EntityPersistenceException;
    Doctor updateDoctorByAdmin(Doctor doctor) throws InvalidEntityDataException, NonexistingEntityException, EntityPersistenceException;
    Doctor deleteDoctorById(Long id) throws NonexistingEntityException;
    Doctor findByUsername(String username) throws EntityPersistenceException;
    long count();
}
