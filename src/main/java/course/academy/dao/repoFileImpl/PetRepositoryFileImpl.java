package course.academy.dao.repoFileImpl;

import course.academy.dao.PetRepository;
import course.academy.dao.idGenerator.IdGenerator;
import course.academy.entities.Pet;

public class PetRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Pet> implements PetRepository {
    public PetRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
