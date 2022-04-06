package course.academy.dao.impl;

import course.academy.dao.ClientRepository;
import course.academy.dao.PetRepository;
import course.academy.entities.Pet;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PetRepositoryJdbc implements PetRepository {
    public static final String SELECT_ALL_PETS = "SELECT * FROM `pets`;";
    public static final String SELECT_PET_BY_ID = "SELECT * FROM `pets` WHERE (id = ?);";
    public static final String SELECT_COUNT_PETS = "SELECT COUNT(*) FROM `pets`;";
    public static final String INSERT_NEW_PET =
            "INSERT INTO `vet_clinic_management_system`.`pets` (`name`, `breed`, `weight`, `client_id`) values (?, ?, ?, ?) ;";
    public static final String UPDATE_PET =
            "UPDATE `vet_clinic_management_system`.`pets` SET `name` = ?, `weight` = ? WHERE (`id` = ?);";
    public static final String DELETE_PET = "DELETE from `pets` WHERE (`id` = ?);";

    private ClientRepository clientRepository;
    private Connection connection;

    public PetRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Pet> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PETS)) {
            ResultSet rs = statement.executeQuery();
            return toPets(rs);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_PETS, e);
        }
    }

    @Override
    public Pet findById(Long id) throws NonexistingEntityException {
        Pet pet = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            pet = toPets(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_PET_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (pet != null) {
            return pet;
        } else {
            throw new NonexistingEntityException("Pet with ID " + id + "cannot be found.");
        }
    }

    @Override
    public Pet create(Pet pet) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_PET, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getBreed());
            statement.setInt(3, pet.getWeight());
            statement.setLong(4, pet.getOwner().getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating pet failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pet.setId(generatedKeys.getLong(1));
                    return pet;
                } else {
                    throw new EntityPersistenceException("Creating pet failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_PET, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_PET, ex);
        }
    }

    @Override
    public Pet update(Pet pet) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PET)) {
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getWeight());
            statement.setLong(5, pet.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating pet failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_PET, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_PET, ex);
        }
        return pet;
    }

    @Override
    public Pet deleteById(Long id) throws NonexistingEntityException {
        Pet deletedPet = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PET)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Deleting pet failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_PET, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_PET, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedPet;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_PETS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public List<Pet> toPets(ResultSet rs) throws SQLException {
        List<Pet> results = new ArrayList<>();
        while (rs.next()) {
            try {
                results.add(new Pet(
                        rs.getLong(1),
                        rs.getString("name"),
                        rs.getString("breed"),
                        rs.getInt("weight"),
                        clientRepository.findById(rs.getLong("client_id"))
                ));
            } catch (NonexistingEntityException e) {
                System.out.println(e.getMessage());
            }
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
    public void addAll(Collection<Pet> entities) {

    }

    @Override
    public void clear() {

    }
}
