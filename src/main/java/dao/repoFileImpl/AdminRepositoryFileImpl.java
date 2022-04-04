package dao.repoFileImpl;

import dao.AdministratorRepository;
import dao.idGenerator.IdGenerator;
import entities.Administrator;

public class AdminRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Administrator> implements AdministratorRepository {
    public AdminRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
