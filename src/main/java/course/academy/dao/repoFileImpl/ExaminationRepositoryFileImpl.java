package course.academy.dao.repoFileImpl;

import course.academy.dao.ExaminationRepository;
import course.academy.dao.idGenerator.IdGenerator;
import course.academy.entities.Examination;

public class ExaminationRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Examination> implements ExaminationRepository {
    public ExaminationRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
