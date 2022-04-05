package course.academy.dao.impl;

import course.academy.dao.AdministratorRepository;
import course.academy.entities.Administrator;
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
public class AdminRepositoryJdbc implements AdministratorRepository {
    public static final String SELECT_ALL_ADMINS = "select * from `administrators`;";
    public static final String SELECT_ADMIN_BY_ID = "SELECT * FROM administrators WHERE (id = ?);";
    public static final String SELECT_COUNT_ADMINS = "SELECT COUNT(*) FROM `administrators`;";
    public static final String INSERT_NEW_ADMIN =
            "INSERT INTO `vet_clinic_management_system`.`administrators` (`first_name`, `last_name`, `email`, `tel_number`, `username`, `password`, `gender`, `role`) values (?, ?, ?, ?, ?, ?, ?, ?) ;";
    public static final String UPDATE_ADMIN =
            "UPDATE `vet_clinic_management_system`.`administrators` SET `first_name` = ?, `last_name` = ?, `tel_number` = ?, `username` = ? WHERE (`id` = ?);";
    public static final String DELETE_ADMIN = "DELETE from `administrators` WHERE (`id` = ?);";

    private Connection connection;

    public AdminRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Administrator> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ADMINS)) {
            ResultSet rs = statement.executeQuery();
            return toAdmins(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_ADMINS, ex);
        }
    }

    @Override
    public Administrator findById(Long id) throws NonexistingEntityException {
        Administrator admin = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ADMIN_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            admin = toAdmins(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ADMIN_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (admin != null) {
            return admin;
        } else {
            throw new NonexistingEntityException("Admin with ID " + id + "cannot be found.");
        }
    }

    @Override
    public Administrator create(Administrator admin) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ADMIN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getTelNumber());
            statement.setString(5, admin.getUsername());
            statement.setString(6, admin.getPassword());
            statement.setString(7, admin.getGender().name());
            statement.setString(8, admin.getRole().name());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating admin failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    admin.setId(generatedKeys.getLong(1));
                    return admin;
                } else {
                    throw new EntityPersistenceException("Creating admin failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_ADMIN, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_ADMIN, ex);
        }
    }

    @Override
    public Administrator update(Administrator admin) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getTelNumber());
            statement.setString(4, admin.getUsername());
            statement.setLong(5, admin.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating admin failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_ADMIN, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_ADMIN, ex);
        }
        return admin;
    }

    @Override
    public Administrator deleteById(Long id) throws NonexistingEntityException {
        Administrator deletedAdmin = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating admin failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_ADMIN, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_ADMIN, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedAdmin;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_ADMINS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    @Override
    public void addAll(Collection<Administrator> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {
    }

    public List<Administrator> toAdmins(ResultSet rs) throws SQLException {
        List<Administrator> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Administrator(
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
}
