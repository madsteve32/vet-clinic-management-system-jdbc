package exception;

public class NonexistingEntityException extends Exception {
    public NonexistingEntityException() {
        super();
    }

    public NonexistingEntityException(String message) {
        super(message);
    }

    public NonexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistingEntityException(Throwable cause) {
        super(cause);
    }
}
