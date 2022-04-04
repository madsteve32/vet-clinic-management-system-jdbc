package dao.repoFileImpl;

import dao.PetRepository;
import dao.idGenerator.IdGenerator;
import entities.Pet;

public class PetRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Pet> implements PetRepository {
    public PetRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
