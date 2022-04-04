package service.impl;

import dao.AppointmentRepository;
import entities.Appointment;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;
import service.AppointmentService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void loadData() {
        appointmentRepository.load();
    }

    @Override
    public void saveData() {
        appointmentRepository.save();
    }

    @Override
    public Collection<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findAllSorted(Comparator<Appointment> comparator) {
        return appointmentRepository.findAllSorted(comparator);
    }

    @Override
    public Appointment getAppointmentById(Long id) throws NonexistingEntityException {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment addAppointment(Appointment appointment) throws InvalidEntityDataException {
        if (appointment.getChosenDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEntityDataException("Appointment chosen date and time cannot be in the past.");
        }
        if (appointment.getChosenDoctor().getAppointments().size() == 10) {
            throw new InvalidEntityDataException("Chosen doctor has no free appointments.");
        }
        Appointment newAppointment = appointmentRepository.create(appointment);
        appointmentRepository.save();
        return newAppointment;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) throws InvalidEntityDataException, NonexistingEntityException {
        if (appointment.getChosenDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEntityDataException("Appointment chosen date and time cannot be in the past.");
        }
        Appointment updatedAppointment = appointmentRepository.update(appointment);
        appointmentRepository.save();
        return updatedAppointment;
    }

    @Override
    public Appointment deleteAppointmentById(Long id) throws NonexistingEntityException {
        Appointment deletedAppointment = appointmentRepository.deleteById(id);
        appointmentRepository.save();
        return deletedAppointment;
    }

    @Override
    public long count() {
        return appointmentRepository.count();
    }
}
