package dao.repoFileImpl;

import dao.PetPassportRepository;
import dao.idGenerator.IdGenerator;
import entities.PetPassport;

public class PetPassportRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, PetPassport>
        implements PetPassportRepository {

    public PetPassportRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
