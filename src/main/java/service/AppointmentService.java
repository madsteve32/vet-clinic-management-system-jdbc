package service;

import entities.Appointment;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface AppointmentService {
    void loadData();
    void saveData();
    Collection<Appointment> findAll();
    List<Appointment> findAllSorted(Comparator<Appointment> comparator);
    Appointment getAppointmentById(Long id) throws NonexistingEntityException;
    Appointment addAppointment(Appointment appointment) throws InvalidEntityDataException;
    Appointment updateAppointment(Appointment appointment) throws InvalidEntityDataException, NonexistingEntityException;
    Appointment deleteAppointmentById(Long id) throws NonexistingEntityException;
    long count();
}
