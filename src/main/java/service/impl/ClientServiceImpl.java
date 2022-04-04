package service.impl;

import dao.AppointmentRepository;
import dao.ClientRepository;
import dao.PetRepository;
import entities.Appointment;
import entities.Client;
import entities.Pet;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;
import service.ClientService;

import java.time.LocalDateTime;
import java.util.Collection;

public class ClientServiceImpl implements ClientService {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{8,20})";
    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    public ClientServiceImpl(ClientRepository clientRepository, AppointmentRepository appointmentRepository, PetRepository petRepository) {
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
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
    public Collection<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Collection<Appointment> myAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(Appointment appointment) throws InvalidEntityDataException {
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
    public Pet addPet(Pet pet) throws InvalidEntityDataException {
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
    public Client addClient(Client client) throws InvalidEntityDataException {
        int firstNameLength = client.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }

        int lastNameLength = client.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }


        if (!client.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidEntityDataException("Email must be valid.");
        }

        int usernameLength = client.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }

        if (!client.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidEntityDataException("Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit");
        }
        Client newClient = clientRepository.create(client);
        clientRepository.save();
        return newClient;
    }

    @Override
    public Client updateClient(Client client) throws NonexistingEntityException, InvalidEntityDataException {
        int firstNameLength = client.getFirstName().length();
        if (firstNameLength < 2 || firstNameLength > 15) {
            throw new InvalidEntityDataException("First name must be between 2 and 15 characters.");
        }

        int lastNameLength = client.getLastName().length();
        if (lastNameLength < 2 || lastNameLength > 15) {
            throw new InvalidEntityDataException("Last name must be between 2 and 15 characters.");
        }


        if (!client.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidEntityDataException("Email must be valid.");
        }

        int usernameLength = client.getUsername().length();
        if (usernameLength < 2 || usernameLength > 15) {
            throw new InvalidEntityDataException("Username must be between 2 and 15 characters.");
        }

        if (!client.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidEntityDataException("Password must be  8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit");
        }
        Client updatedClient = clientRepository.update(client);
        clientRepository.save();
        return updatedClient;
    }

    @Override
    public Client deleteClientById(Long id) throws NonexistingEntityException {
        Client deletedClient = clientRepository.deleteById(id);
        clientRepository.save();
        return deletedClient;
    }

    @Override
    public Client findByUsername(String username) {
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
