package course.academy.dao.repoFileImpl;

import course.academy.dao.DoctorRepository;
import course.academy.dao.idGenerator.IdGenerator;
import course.academy.entities.Doctor;

public class DoctorRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Doctor> implements DoctorRepository {
    public DoctorRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
