package dao.impl;

import dao.AppointmentRepository;
import entities.Appointment;
import exception.NonexistingEntityException;

import java.util.*;

public class AppointmentRepositoryImpl implements AppointmentRepository {
    Map<Long, Appointment> appointments = new HashMap<>();

    @Override
    public Collection<Appointment> findAll() {
        return appointments.values();
    }

    @Override
    public List<Appointment> findAllSorted(Comparator<Appointment> comparator) {
        ArrayList<Appointment> sorted = new ArrayList<>(appointments.values());
        sorted.sort(comparator);
        return sorted;
    }

    @Override
    public Appointment findById(Long id) throws NonexistingEntityException {
        Appointment appointment = appointments.get(id);
        if (appointment == null) {
            throw new NonexistingEntityException("Appointment with ID'" + id + "' does not exist.");
        }
        return appointment;
    }

    @Override
    public Appointment create(Appointment appointment) {
        appointments.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public Appointment update(Appointment appointment) throws NonexistingEntityException {
        Appointment oldAppointment = findById(appointment.getId());
        if (oldAppointment == null) {
            throw new NonexistingEntityException("Appointment with ID'" + appointment.getId() + "' does not exist.");
        }
        appointments.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public Appointment deleteById(Long id) throws NonexistingEntityException {
        Appointment appointment = appointments.remove(id);
        if (appointment == null) {
            throw new NonexistingEntityException("Appointment with ID'" + id + "' does not exist.");
        }
        return appointment;
    }

    @Override
    public long count() {
        return appointments.size();
    }

    @Override
    public void addAll(Collection<Appointment> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
