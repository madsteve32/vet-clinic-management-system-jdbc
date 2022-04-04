package service;

import entities.Appointment;
import entities.Doctor;
import entities.Examination;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;

import java.util.Collection;

public interface DoctorService {
    void loadData();
    void saveData();
    Collection<Doctor> findAll();
    Appointment updateAppointmentInfo(Appointment appointment) throws NonexistingEntityException;
    Examination createExamination(Examination examination) throws InvalidEntityDataException;
    Doctor getDoctorById(Long id) throws NonexistingEntityException;
    Doctor addDoctor(Doctor doctor) throws InvalidEntityDataException;
    Doctor updateDoctor(Doctor doctor) throws InvalidEntityDataException, NonexistingEntityException;
    Doctor deleteDoctorById(Long id) throws NonexistingEntityException;
    Doctor findByUsername(String username);
    long count();
}
