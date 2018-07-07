package gnova.data;

public class BatchException extends Exception {

    public BatchException() {
        super();
    }

    public BatchException(String message) {
        super(message);
    }

    public BatchException(Throwable throwable) {
        super(throwable);
    }

    public BatchException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
