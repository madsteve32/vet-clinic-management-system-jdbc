package course.academy.service.impl;

import course.academy.dao.AppointmentRepository;
import course.academy.entities.Appointment;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.AppointmentService;

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
    public Collection<Appointment> findAll() throws EntityPersistenceException {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findAllSorted(Comparator<Appointment> comparator) throws EntityPersistenceException {
        return appointmentRepository.findAllSorted(comparator);
    }

    @Override
    public Appointment getAppointmentById(Long id) throws NonexistingEntityException {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment addAppointment(Appointment appointment) throws InvalidEntityDataException, EntityPersistenceException {
        Collection<Appointment> appointments = appointmentRepository.findAll();
        int hour = appointment.getChosenDateTime().getHour();
        if (hour < 8 || hour > 18) {
            throw new InvalidEntityDataException("Doctor working time is from (08:00 to 18:00) please choose another time.");
        }
        if (appointment.getChosenDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEntityDataException("Appointment chosen date and time cannot be in the past.");
        }
        for (Appointment bookedAppointment : appointments) {
            if (bookedAppointment.getChosenDateTime().equals(appointment.getChosenDateTime())) {
                throw new InvalidEntityDataException("Appointment chosen date and time is already taken.");
            }
        }
        Appointment newAppointment = appointmentRepository.create(appointment);
        appointmentRepository.save();
        return newAppointment;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) throws InvalidEntityDataException, NonexistingEntityException, EntityPersistenceException {
        if (appointment.getChosenDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEntityDataException("Appointment chosen date and time cannot be in the past.");
        }
        Appointment updatedAppointment = appointmentRepository.update(appointment);
        appointmentRepository.save();
        return updatedAppointment;
    }

    @Override
    public Appointment updateAppointmentExamination(Appointment appointment) throws EntityPersistenceException {
        Appointment updatedAppointment = appointmentRepository.updateExamination(appointment);
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
