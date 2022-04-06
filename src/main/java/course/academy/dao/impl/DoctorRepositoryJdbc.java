package course.academy.dao.impl;

import course.academy.dao.DoctorRepository;
import course.academy.entities.Doctor;
import course.academy.entities.enums.Gender;
import course.academy.entities.enums.Role;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class DoctorRepositoryJdbc implements DoctorRepository {
    public static final String SELECT_ALL_DOCTORS = "SELECT * FROM `doctors`;";
    public static final String SELECT_DOCTOR_BY_ID = "SELECT * FROM `doctors` WHERE (id = ?);";
    public static final String SELECT_COUNT_DOCTORS = "SELECT COUNT(*) FROM `doctors`;";
    public static final String INSERT_NEW_DOCTOR =
            "INSERT INTO `vet_clinic_management_system`.`doctors` (`first_name`, `last_name`, `email`, `tel_number`, `username`, `password`, `gender`, `role`) values (?, ?, ?, ?, ?, ?, ?, ?) ;";
    public static final String UPDATE_DOCTOR =
            "UPDATE `vet_clinic_management_system`.`doctors` SET `first_name` = ?, `last_name` = ?, `tel_number` = ?, `username` = ? WHERE (`id` = ?);";
    public static final String DELETE_DOCTOR = "DELETE from `doctors` WHERE (`id` = ?);";

    private Connection connection;

    public DoctorRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Doctor> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DOCTORS)) {
            ResultSet rs = statement.executeQuery();
            return toDoctors(rs);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_DOCTORS, e);
        }
    }

    @Override
    public Doctor findById(Long id) throws NonexistingEntityException {
        Doctor doctor = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_DOCTOR_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            doctor = toDoctors(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_DOCTOR_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (doctor != null) {
            return doctor;
        } else {
            throw new NonexistingEntityException("Doctor with ID " + id + "cannot be found.");
        }
    }

    @Override
    public Doctor create(Doctor doctor) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_DOCTOR, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setString(3, doctor.getEmail());
            statement.setString(4, doctor.getTelNumber());
            statement.setString(5, doctor.getUsername());
            statement.setString(6, doctor.getPassword());
            statement.setString(7, doctor.getGender().name());
            statement.setString(8, doctor.getRole().name());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating doctor failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    doctor.setId(generatedKeys.getLong(1));
                    return doctor;
                } else {
                    throw new EntityPersistenceException("Creating doctor failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_DOCTOR, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_DOCTOR, ex);
        }
    }

    @Override
    public Doctor update(Doctor doctor) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DOCTOR)) {
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setString(3, doctor.getTelNumber());
            statement.setString(4, doctor.getUsername());
            statement.setLong(5, doctor.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating doctor failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_DOCTOR, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_DOCTOR, ex);
        }
        return doctor;
    }

    @Override
    public Doctor deleteById(Long id) throws NonexistingEntityException {
        Doctor deletedDoctor = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_DOCTOR)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Deleting doctor failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_DOCTOR, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_DOCTOR, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedDoctor;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_DOCTORS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public List<Doctor> toDoctors(ResultSet rs) throws SQLException {
        List<Doctor> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Doctor(
                    rs.getLong(1),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("tel_number"),
                    rs.getString("username"),
                    rs.getString("password"),
                    Gender.valueOf(rs.getString("gender")),
                    Role.valueOf(rs.getString("role"))
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
    public void addAll(Collection<Doctor> entities) {

    }

    @Override
    public void clear() {

    }
}
