package dao;

import entities.Appointment;

import java.util.Comparator;
import java.util.List;

public interface AppointmentRepository extends PersistableRepository<Long, Appointment> {
    List<Appointment> findAllSorted(Comparator<Appointment> comparator);
}
