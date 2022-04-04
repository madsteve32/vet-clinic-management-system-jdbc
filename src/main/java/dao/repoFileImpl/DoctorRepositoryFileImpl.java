package dao.repoFileImpl;

import dao.DoctorRepository;
import dao.idGenerator.IdGenerator;
import entities.Doctor;

public class DoctorRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Doctor> implements DoctorRepository {
    public DoctorRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
