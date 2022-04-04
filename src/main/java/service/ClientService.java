package service;

import entities.Appointment;
import entities.Client;
import entities.Pet;
import exception.InvalidEntityDataException;
import exception.NonexistingEntityException;

import java.util.Collection;

public interface ClientService {
    void loadData();
    void saveData();
    Collection<Client> findAll();
    Collection<Appointment> myAppointments();
    Appointment createAppointment(Appointment appointment) throws InvalidEntityDataException;
    Appointment deleteAppointment(Long id) throws NonexistingEntityException;
    Pet addPet(Pet pet) throws InvalidEntityDataException;
    Client getClientById(Long id) throws NonexistingEntityException;
    Client addClient(Client client) throws InvalidEntityDataException;
    Client updateClient(Client client) throws NonexistingEntityException, InvalidEntityDataException;
    Client deleteClientById(Long id) throws NonexistingEntityException;
    Client findByUsername(String username);
    long count();
}
