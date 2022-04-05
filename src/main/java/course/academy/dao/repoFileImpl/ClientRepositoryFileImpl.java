package course.academy.dao.repoFileImpl;

import course.academy.dao.ClientRepository;
import course.academy.dao.idGenerator.IdGenerator;
import course.academy.entities.Client;

public class ClientRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Client> implements ClientRepository {
    public ClientRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
