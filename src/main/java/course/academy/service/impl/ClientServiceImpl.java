package course.academy.service.impl;

import course.academy.dao.AppointmentRepository;
import course.academy.dao.ClientRepository;
import course.academy.dao.PetRepository;
import course.academy.entities.Appointment;
import course.academy.entities.Client;
import course.academy.entities.Pet;
import course.academy.exception.ConstraintViolationException;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;
import course.academy.service.ClientService;
import course.academy.util.ClientValidator;

import java.time.LocalDateTime;
import java.util.Collection;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final ClientValidator clientValidator;

    public ClientServiceImpl(ClientRepository clientRepository, AppointmentRepository appointmentRepository, PetRepository petRepository, ClientValidator clientValidator) {
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.clientValidator = clientValidator;
    }

    @Override
    public void loadData() {
        clientRepository.load();
    }

    @Override
    public void saveData() {
        clientRepository.save();
    }

    @Override
    public Collection<Client> findAll() throws EntityPersistenceException {
        return clientRepository.findAll();
    }

    @Override
    public Collection<Appointment> myAppointments() throws EntityPersistenceException {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(Appointment appointment) throws InvalidEntityDataException, EntityPersistenceException {
        Collection<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment bookedAppointment : appointments) {
            if (appointment.getChosenDateTime().equals(bookedAppointment.getChosenDateTime())) {
                throw new InvalidEntityDataException("Chosen date and time is already booked.");
            }
        }

        if (appointment.getChosenDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEntityDataException("Appointment date and time cannot be in the past.");
        }
        return appointmentRepository.create(appointment);
    }

    @Override
    public Appointment deleteAppointment(Long id) throws NonexistingEntityException {
        return appointmentRepository.deleteById(id);
    }

    @Override
    public Pet addPet(Pet pet) throws InvalidEntityDataException, EntityPersistenceException {
        if (pet.getWeight() <= 0) {
            throw new InvalidEntityDataException("Pet weight must be positive number.");
        }
        return petRepository.create(pet);
    }

    @Override
    public Client getClientById(Long id) throws NonexistingEntityException {
        return clientRepository.findById(id);
    }

    @Override
    public Client addClient(Client client) throws InvalidEntityDataException, EntityPersistenceException {
        try {
            clientValidator.validate(client);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityDataException(
                    String.format("Error creating client '%s'", client.getUsername()), e);
        }
        Client newClient = clientRepository.create(client);
        clientRepository.save();
        return newClient;
    }

    @Override
    public Client updateClient(Client client) throws NonexistingEntityException, InvalidEntityDataException, EntityPersistenceException {
        try {
            clientValidator.validate(client);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityDataException(
                    String.format("Error updating client '%s'", client.getUsername()), e);
        }
        Client updatedClient = clientRepository.update(client);
        clientRepository.save();
        return updatedClient;
    }

    @Override
    public Client updateClientByAdmin(Client client) throws NonexistingEntityException, EntityPersistenceException, InvalidEntityDataException {
        int firstNameLength = client.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }
        int lastNameLength = client.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }
        int usernameLength = client.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }
        return clientRepository.updateByAdmin(client);
    }

    @Override
    public Client deleteClientById(Long id) throws NonexistingEntityException {
        Client deletedClient = clientRepository.deleteById(id);
        clientRepository.save();
        return deletedClient;
    }

    @Override
    public Client findByUsername(String username) throws EntityPersistenceException {
        Collection<Client> allClients = clientRepository.findAll();
        for (Client client : allClients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public long count() {
        return clientRepository.count();
    }
}
