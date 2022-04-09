package course.academy.dao;

import course.academy.entities.Appointment;
import course.academy.exception.EntityPersistenceException;

import java.util.Comparator;
import java.util.List;

public interface AppointmentRepository extends PersistableRepository<Long, Appointment> {
    List<Appointment> findAllSorted(Comparator<Appointment> comparator) throws EntityPersistenceException;
    Appointment updateExamination(Appointment appointment) throws EntityPersistenceException;
}
