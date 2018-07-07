package gnova.data.index;

/**
 * 索引异常
 */
public class IndexException
        extends Exception {

    public IndexException() {
        super();
    }

    public IndexException(String message) {
        super(message);
    }

    public IndexException(Throwable throwable) {
        super(throwable);
    }

    public IndexException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
