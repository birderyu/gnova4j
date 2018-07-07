package gnova.orm;

/**
 * ORM过程中抛出的异常
 *
 * @see Exception
 * @author birderyu
 * @version 1.0.0
 */
public class ORMException
        extends Exception {

    /**
     * 构建一个ORM过程中抛出的异常
     *
     * @see Exception#Exception()
     */
    public ORMException() {
        super();
    }

    /**
     * 构建一个ORM过程中抛出的异常
     *
     * @param message 异常信息
     * @see Exception#Exception(String)
     */
    public ORMException(String message) {
        super(message);
    }

    /**
     * 构建一个ORM过程中抛出的异常
     *
     * @param throwable 引起异常的原因
     * @see Exception#Exception(Throwable)
     */
    public ORMException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构建一个ORM过程中抛出的异常
     *
     * @param message 异常信息
     * @param throwable 引起异常的原因
     * @see Exception#Exception(String, Throwable)
     */
    public ORMException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
