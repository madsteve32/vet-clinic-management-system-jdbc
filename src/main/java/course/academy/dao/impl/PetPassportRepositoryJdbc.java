package course.academy.dao.impl;

import course.academy.dao.PetPassportRepository;
import course.academy.entities.PetPassport;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PetPassportRepositoryJdbc implements PetPassportRepository {
    public static final String SELECT_ALL_PASSPORTS = "SELECT * FROM `pet_passports`;";
    public static final String SELECT_PASSPORT_BY_ID = "SELECT * FROM `pet_passports` WHERE (id = ?);";
    public static final String SELECT_PASSPORT_BY_PET_ID = "SELECT * FROM `pet_passports` WHERE (pets_id = ?);";
    public static final String SELECT_COUNT_PASSPORTS = "SELECT COUNT(*) FROM `pet_passports`;";
    public static final String INSERT_NEW_PASSPORT =
            "INSERT INTO `vet_clinic_management_system`.`pet_passports` (`deworming_date`, `vaccination_date`, `examination_date`, `pets_id`, `pets_client_id`) values (?, ?, ?, ?, ?) ;";
    public static final String UPDATE_PASSPORT =
            "UPDATE `vet_clinic_management_system`.`pet_passports` SET `deworming_date` = ?, `vaccination_date` = ?, `examination_date` = ? WHERE (`id` = ?);";
    public static final String DELETE_PASSPORT = "DELETE from `pet_passports` WHERE (`id` = ?);";

    private Connection connection;

    public PetPassportRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<PetPassport> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PASSPORTS)) {
            ResultSet rs = statement.executeQuery();
            return toPassports(rs);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_PASSPORTS, e);
        }
    }

    @Override
    public PetPassport findById(Long id) throws NonexistingEntityException {
        PetPassport petPassport = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PASSPORT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            petPassport = toPassports(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_PASSPORT_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (petPassport != null) {
            return petPassport;
        } else {
            throw new NonexistingEntityException("Passport with ID " + id + "cannot be found.");
        }
    }

    @Override
    public PetPassport findByPetId(Long id) throws NonexistingEntityException {
        PetPassport petPassport = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PASSPORT_BY_PET_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            petPassport = toPassports(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_PASSPORT_BY_PET_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (petPassport != null) {
            return petPassport;
        } else {
            throw new NonexistingEntityException("Passport with ID " + id + "cannot be found.");
        }
    }

    @Override
    public PetPassport create(PetPassport passport) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_PASSPORT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(passport.getDewormingDate()));
            statement.setDate(2, Date.valueOf(passport.getVaccinationDate()));
            statement.setDate(3, Date.valueOf(passport.getExaminationDate()));
            statement.setLong(4, passport.getPetId());
            statement.setLong(5, passport.getClientId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating passport failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    passport.setId(generatedKeys.getLong(1));
                    return passport;
                } else {
                    throw new EntityPersistenceException("Creating passport failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_PASSPORT, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_PASSPORT, ex);
        }
    }

    @Override
    public PetPassport update(PetPassport passport) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PASSPORT)) {
            statement.setDate(1, Date.valueOf(passport.getDewormingDate()));
            statement.setDate(2, Date.valueOf(passport.getVaccinationDate()));
            statement.setDate(3, Date.valueOf(passport.getExaminationDate()));
            statement.setLong(5, passport.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating passport failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_PASSPORT, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_PASSPORT, ex);
        }
        return passport;
    }

    @Override
    public PetPassport deleteById(Long id) throws NonexistingEntityException {
        PetPassport deletedPassport = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PASSPORT)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Deleting passport failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_PASSPORT, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_PASSPORT, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedPassport;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_PASSPORTS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public List<PetPassport> toPassports(ResultSet rs) throws SQLException {
        List<PetPassport> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new PetPassport(
                    rs.getLong(1),
                    rs.getDate("deworming_date").toLocalDate(),
                    rs.getDate("vaccination_date").toLocalDate(),
                    rs.getDate("examination_date").toLocalDate(),
                    rs.getLong("pets_id"),
                    rs.getLong("pets_client_id")
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
    public void addAll(Collection<PetPassport> entities) {

    }

    @Override
    public void clear() {

    }
}
