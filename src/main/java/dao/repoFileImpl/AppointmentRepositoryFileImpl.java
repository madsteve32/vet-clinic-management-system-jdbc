package dao.repoFileImpl;

import dao.AppointmentRepository;
import dao.idGenerator.IdGenerator;
import entities.Appointment;

import java.util.Comparator;
import java.util.List;

public class AppointmentRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Appointment> implements AppointmentRepository {
    public AppointmentRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }

    @Override
    public List<Appointment> findAllSorted(Comparator<Appointment> comparator) {
        return null;
    }
}
