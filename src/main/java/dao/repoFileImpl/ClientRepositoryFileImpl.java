package dao.repoFileImpl;

import dao.ClientRepository;
import dao.idGenerator.IdGenerator;
import entities.Client;

public class ClientRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Client> implements ClientRepository {
    public ClientRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
