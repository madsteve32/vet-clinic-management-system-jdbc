package course.academy.dao.repoFileImpl;

import course.academy.dao.AdministratorRepository;
import course.academy.dao.idGenerator.IdGenerator;
import course.academy.entities.Administrator;

public class AdminRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Administrator> implements AdministratorRepository {
    public AdminRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
