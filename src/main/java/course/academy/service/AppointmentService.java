package course.academy.service;

import course.academy.entities.Appointment;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface AppointmentService {
    void loadData();
    void saveData();
    Collection<Appointment> findAll() throws EntityPersistenceException;
    List<Appointment> findAllSorted(Comparator<Appointment> comparator) throws EntityPersistenceException;
    Appointment getAppointmentById(Long id) throws NonexistingEntityException;
    Appointment addAppointment(Appointment appointment) throws InvalidEntityDataException, EntityPersistenceException;
    Appointment updateAppointment(Appointment appointment) throws InvalidEntityDataException, NonexistingEntityException, EntityPersistenceException;
    Appointment updateAppointmentExamination(Appointment appointment) throws EntityPersistenceException;
    Appointment deleteAppointmentById(Long id) throws NonexistingEntityException;
    long count();
}
