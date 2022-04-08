package course.academy.dao.impl;

import course.academy.dao.ExaminationRepository;
import course.academy.entities.Appointment;
import course.academy.entities.Examination;
import course.academy.entities.enums.Status;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ExaminationRepositoryJdbc implements ExaminationRepository {
    public static final String SELECT_ALL_EXAMINATIONS = "select * from `examinations`;";
    public static final String SELECT_EXAMINATION_BY_ID = "SELECT * FROM `examinations` WHERE (id = ?);";
    public static final String SELECT_COUNT_EXAMINATIONS = "SELECT COUNT(*) FROM `examinations`;";
    public static final String INSERT_NEW_EXAMINATION =
            "INSERT INTO `vet_clinic_management_system`.`examinations` (`name`, `date`, `status`) values (?, ?, ?) ;";
    public static final String UPDATE_EXAMINATION =
            "UPDATE `vet_clinic_management_system`.`examinations` SET `status` = ? WHERE (`id` = ?);";
    public static final String DELETE_EXAMINATION = "DELETE from `examinations` WHERE (`id` = ?);";

    private Connection connection;

    public ExaminationRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Examination> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EXAMINATIONS)) {
            ResultSet rs = statement.executeQuery();
            return toExaminations(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_EXAMINATIONS, ex);
        }
    }

    @Override
    public Examination findById(Long id) throws NonexistingEntityException {
        Examination examination = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_EXAMINATION_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            examination = toExaminations(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_EXAMINATION_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (examination != null) {
            return examination;
        } else {
            throw new NonexistingEntityException("Examination with ID " + id + "cannot be found.");
        }
    }

    @Override
    public Examination create(Examination examination) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_EXAMINATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, examination.getName());
            statement.setDate(2, Date.valueOf(examination.getDate()));
            statement.setString(3, examination.getStatus().name());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating examination failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    examination.setId(generatedKeys.getLong(1));
                    return examination;
                } else {
                    throw new EntityPersistenceException("Creating examination failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_EXAMINATION, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_EXAMINATION, ex);
        }
    }

    @Override
    public Examination update(Examination examination) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXAMINATION)) {
            statement.setString(1, examination.getStatus().name());
            statement.setLong(2, examination.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating examination failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_EXAMINATION, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_EXAMINATION, ex);
        }
        return examination;
    }

    @Override
    public Examination deleteById(Long id) throws NonexistingEntityException {
        Examination deletedExamination = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_EXAMINATION)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Deleting examination failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_EXAMINATION, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_EXAMINATION, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedExamination;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_EXAMINATIONS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public List<Examination> toExaminations(ResultSet rs) throws SQLException {
        List<Examination> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Examination(
                    rs.getLong(1),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    Status.valueOf(rs.getString("status"))
            ));
        }
        return results;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public void addAll(Collection<Examination> entities) {

    }

    @Override
    public void clear() {

    }
}
