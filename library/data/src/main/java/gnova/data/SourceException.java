package gnova.data;

public class SourceException
        extends Exception {

    public SourceException() {
        super();
    }

    public SourceException(String message) {
        super(message);
    }

    public SourceException(Throwable throwable) {
        super(throwable);
    }

    public SourceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
