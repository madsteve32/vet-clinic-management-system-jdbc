package dao.impl;

import dao.ClientRepository;
import entities.Client;
import exception.NonexistingEntityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientRepositoryImpl implements ClientRepository {
    Map<Long, Client> clients = new HashMap<>();

    @Override
    public Collection<Client> findAll() {
        return clients.values();
    }

    @Override
    public Client findById(Long id) throws NonexistingEntityException {
        Client client = clients.get(id);
        if (client == null) {
            throw new NonexistingEntityException("Client with ID'" + id + "' does not exist.");
        }
        return client;
    }

    @Override
    public Client create(Client client) {
        clients.put(client.getId(), client);
        return client;
    }

    @Override
    public void addAll(Collection<Client> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Client update(Client client) throws NonexistingEntityException {
        Client oldClient = findById(client.getId());
        if (oldClient == null) {
            throw new NonexistingEntityException("Client with ID'" + client.getId() + "' does not exist.");
        }
        clients.put(client.getId(), client);
        return client;
    }

    @Override
    public Client deleteById(Long id) throws NonexistingEntityException {
        Client client = clients.remove(id);
        if (client == null) {
            throw new NonexistingEntityException("Client with ID'" + id + "' does not exist.");
        }
        return client;
    }

    @Override
    public long count() {
        return clients.size();
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
