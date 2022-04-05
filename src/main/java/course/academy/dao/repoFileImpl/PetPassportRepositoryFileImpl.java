package course.academy.dao.repoFileImpl;

import course.academy.dao.PetPassportRepository;
import course.academy.dao.idGenerator.IdGenerator;
import course.academy.entities.PetPassport;

public class PetPassportRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, PetPassport>
        implements PetPassportRepository {

    public PetPassportRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
