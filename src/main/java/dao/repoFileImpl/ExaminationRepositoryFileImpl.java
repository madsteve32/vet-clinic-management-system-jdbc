package dao.repoFileImpl;

import dao.ExaminationRepository;
import dao.idGenerator.IdGenerator;
import entities.Examination;

public class ExaminationRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Examination> implements ExaminationRepository {
    public ExaminationRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
