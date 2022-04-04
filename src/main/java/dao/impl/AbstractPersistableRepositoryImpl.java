package dao.impl;

import dao.Identifiable;
import dao.PersistableRepository;
import dao.idGenerator.IdGenerator;
import exception.NonexistingEntityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPersistableRepositoryImpl<K, V extends Identifiable<K>>
        implements PersistableRepository<K, V> {
    private Map<K, V> entities = new HashMap<>();
    private IdGenerator<K> idGenerator;

    public AbstractPersistableRepositoryImpl(IdGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
    }


    @Override
    public Collection<V> findAll() {
        return entities.values();
    }

    @Override
    public V findById(K id) throws NonexistingEntityException {
        return entities.get(id);
    }

    @Override
    public V create(V entity) {
        entity.setId(idGenerator.getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void addAll(Collection<V> entitiesCollection) {
        for (var entity : entitiesCollection) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public void clear() {
        entities.clear();
    }

    @Override
    public V update(V entity) throws NonexistingEntityException {
        V old = findById(entity.getId());
        if (old == null) {
            throw new NonexistingEntityException("Entity with ID='" + entity.getId() + "' does not exist.");
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public V deleteById(K id) throws NonexistingEntityException {
        V old = entities.remove(id);
        if(old == null) {
            throw new NonexistingEntityException("Entity with ID='" + id + "' does not exist.");
        }
        return old;
    }

    @Override
    public long count() {
        return entities.size();
    }

    public IdGenerator<K> getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IdGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
    }
}
