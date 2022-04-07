package course.academy.dao.impl;

import course.academy.dao.AppointmentRepository;
import course.academy.entities.Administrator;
import course.academy.entities.Appointment;
import course.academy.entities.enums.ExaminationType;
import course.academy.entities.enums.Status;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class AppointmentRepositoryJdbc implements AppointmentRepository {
    public static final String SELECT_ALL_APPOINTMENTS = "select * from `appointments`;";
    public static final String SELECT_APPOINTMENT_BY_ID = "SELECT * FROM `appointments` WHERE (id = ?);";
    public static final String SELECT_COUNT_APPOINTMENTS = "SELECT COUNT(*) FROM `appointments`;";
    public static final String INSERT_NEW_APPOINTMENT =
            "INSERT INTO `vet_clinic_management_system`.`appointments` (`examination_type`, `chosen_date_time`, `status`, `doctor_id`, `client_id`) values (?, ?, ?, ?, ?) ;";
    public static final String UPDATE_APPOINTMENT =
            "UPDATE `vet_clinic_management_system`.`appointments` SET `status` = ? WHERE (`id` = ?);";
    public static final String DELETE_APPOINTMENT = "DELETE from `appointments` WHERE (`id` = ?);";

    private Connection connection;

    public AppointmentRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Appointment> findAllSorted(Comparator<Appointment> comparator) {
        return null;
    }

    @Override
    public Collection<Appointment> findAll() throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_APPOINTMENTS)) {
            ResultSet rs = statement.executeQuery();
            return toAppointments(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_APPOINTMENTS, ex);
        }
    }

    @Override
    public Appointment findById(Long id) throws NonexistingEntityException {
        Appointment appointment = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_APPOINTMENT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            appointment = toAppointments(rs).get(0);
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + SELECT_APPOINTMENT_BY_ID, e);
            } catch (EntityPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (appointment != null) {
            return appointment;
        } else {
            throw new NonexistingEntityException("Appointment with ID " + id + "cannot be found.");
        }
    }

    @Override
    public Appointment create(Appointment appointment) throws EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_APPOINTMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, appointment.getExaminationType().name());
            statement.setTimestamp(2, Timestamp.valueOf(appointment.getChosenDateTime()));
            statement.setString(3, appointment.getStatus().name());
            statement.setLong(4, appointment.getChosenDoctorId());
            statement.setLong(5, appointment.getClientId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Creating appointment failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setId(generatedKeys.getLong(1));
                    return appointment;
                } else {
                    throw new EntityPersistenceException("Creating appointment failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_APPOINTMENT, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_APPOINTMENT, ex);
        }
    }

    @Override
    public Appointment update(Appointment appointment) throws NonexistingEntityException, EntityPersistenceException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_APPOINTMENT)) {
            statement.setString(1, appointment.getExaminationType().name());
            statement.setLong(2, appointment.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Updating appointment failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_APPOINTMENT, e);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_APPOINTMENT, ex);
        }
        return appointment;
    }

    @Override
    public Appointment deleteById(Long id) throws NonexistingEntityException {
        Appointment deletedAppointment = findById(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_APPOINTMENT)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try {
                    throw new EntityPersistenceException("Deleting appointment failed, no rows affected.");
                } catch (EntityPersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                try {
                    throw new EntityPersistenceException("Error executing SQL query: " + DELETE_APPOINTMENT, e);
                } catch (EntityPersistenceException entityPersistenceException) {
                    System.out.println(entityPersistenceException.getMessage());
                }
            }
            log.error("Error creating connection to DB", ex);
            try {
                throw new EntityPersistenceException("Error executing SQL query: " + DELETE_APPOINTMENT, ex);
            } catch (EntityPersistenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return deletedAppointment;
    }

    @Override
    public long count() {
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_APPOINTMENTS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public List<Appointment> toAppointments(ResultSet rs) throws SQLException {
        List<Appointment> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Appointment(
                    rs.getLong(1),
                    ExaminationType.valueOf(rs.getString("examination_type")),
                    rs.getTimestamp("chosen_date_time").toLocalDateTime(),
                    Status.valueOf(rs.getString("status")),
                    rs.getLong("doctor_id"),
                    rs.getLong("client_id")
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
    public void addAll(Collection<Appointment> entities) {

    }

    @Override
    public void clear() {

    }
}
