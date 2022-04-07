package course.academy.dao;

import course.academy.entities.Client;
import course.academy.exception.EntityPersistenceException;
import course.academy.exception.NonexistingEntityException;

public interface ClientRepository extends PersistableRepository<Long, Client> {
    Client updateByAdmin(Client client) throws NonexistingEntityException, EntityPersistenceException;
}
