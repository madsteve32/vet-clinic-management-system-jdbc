package course.academy.service;

import course.academy.entities.Appointment;
import course.academy.entities.Client;
import course.academy.entities.Pet;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.InvalidEntityDataException;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;

public interface ClientService {
    void loadData();
    void saveData();
    Collection<Client> findAll() throws EntityPersistenceException;
    Collection<Appointment> myAppointments() throws EntityPersistenceException;
    Appointment createAppointment(Appointment appointment) throws InvalidEntityDataException, EntityPersistenceException;
    Appointment deleteAppointment(Long id) throws NonexistingEntityException;
    Pet addPet(Pet pet) throws InvalidEntityDataException, EntityPersistenceException;
    Client getClientById(Long id) throws NonexistingEntityException;
    Client addClient(Client client) throws InvalidEntityDataException, EntityPersistenceException;
    Client updateClient(Client client) throws NonexistingEntityException, InvalidEntityDataException, EntityPersistenceException;
    Client updateClientByAdmin(Client client) throws NonexistingEntityException, EntityPersistenceException, InvalidEntityDataException;
    Client deleteClientById(Long id) throws NonexistingEntityException;
    Client findByUsername(String username) throws EntityPersistenceException;
    long count();
}
