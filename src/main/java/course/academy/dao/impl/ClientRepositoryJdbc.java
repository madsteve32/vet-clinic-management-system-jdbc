package course.academy.dao.impl;

import course.academy.dao.ClientRepository;
import course.academy.entities.Administrator;
import course.academy.entities.Client;
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
public class ClientRepositoryJdbc implements ClientRepository {
    public static final String SELECT_ALL_CLIENTS = "SELECT * FROM `clients`;";
    public static final String SELECT_CLIENT_BY_ID = "SELECT * FROM `administrators` WHERE (id = ?);";
    public static final String SELECT_COUNT_CLIENTS = "SELECT COUNT(*) FROM `administrators`;";
    public static final String INSERT_NEW_CLIENT =
            "INSERT INTO `vet_clinic_management_system`.`clients` (`first_name`, `last_name`, `email`, `tel_number`, `username`, `password`, `gender`, `role`) values (?, ?, ?, ?, ?, ?, ?, ?) ;";
    public static final String UPDATE_CLIENT =
            "UPDATE `vet_clinic_management_system`.`clients` SET `first_name` = ?, `last_name` = ?, `tel_number` = ?, `username` = ? WHERE (`id` = ?);";
    public static final String DELETE_CLIENT = "DELETE from `administrators` WHERE (`id` = ?);";

    private Connection connection;

    public ClientRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Client> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CLIENTS)) {
            ResultSet rs = statement.executeQuery();
            return toClients(rs);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_CLIENTS, e);
        }
    }

    @Override
    public Client findById(Long id) throws NonexistingEntityException {
        Client client = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            client = toClients(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_CLIENT_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (client != null) {
            return client;
        } else {
            throw new NonexistingEntityException("Client with ID " + id + "cannot be found.");
        }
    }

    @Override
    public Client create(Client client) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getTelNumber());
            statement.setString(5, client.getUsername());
            statement.setString(6, client.getPassword());
            statement.setString(7, client.getGender().name());
            statement.setString(8, client.getRole().name());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating client failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));
                    return client;
                } else {
                    throw new EntityPersistenceException("Creating client failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_CLIENT, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_CLIENT, ex);
        }
    }

    @Override
    public Client update(Client client) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getTelNumber());
            statement.setString(4, client.getUsername());
            statement.setLong(5, client.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating client failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_CLIENT, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_CLIENT, ex);
        }
        return client;
    }

    @Override
    public Client deleteById(Long id) throws NonexistingEntityException {
        Client deletedClient = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Deleting client failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_CLIENT, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_CLIENT, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedClient;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_CLIENTS)) {
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
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public void addAll(Collection<Client> entities) {

    }

    @Override
    public void clear() {

    }

    public List<Client> toClients(ResultSet rs) throws SQLException {
        List<Client> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Client(
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
