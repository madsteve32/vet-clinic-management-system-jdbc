package exception;

public class EntityPersistenceException extends Exception {
    public EntityPersistenceException() {
        super();
    }

    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityPersistenceException(Throwable cause) {
        super(cause);
    }
}
