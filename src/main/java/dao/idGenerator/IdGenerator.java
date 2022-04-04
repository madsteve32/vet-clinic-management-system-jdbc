package dao.idGenerator;

public interface IdGenerator<K> {
    K getNextId();
    K getCurrentId();
    void reset(K newValue);
}
