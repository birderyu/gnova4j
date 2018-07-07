package gnova.json;

/**
 * JSON解析过程中抛出的异常
 *
 * @see Exception
 * @author birderyu
 * @version 1.0.0
 */
public class JsonParseException
        extends Exception {

    /**
     * 构建一个JSON解析过程中抛出的异常
     *
     * @see Exception#Exception()
     */
    public JsonParseException() {
        super();
    }

    /**
     * 构建一个JSON解析过程中抛出的异常
     *
     * @param message 异常信息
     * @see Exception#Exception(String)
     */
    public JsonParseException(String message) {
        super(message);
    }

    /**
     * 构建一个JSON解析过程中抛出的异常
     *
     * @param throwable 引起异常的原因
     * @see Exception#Exception(Throwable)
     */
    public JsonParseException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构建一个JSON解析过程中抛出的异常
     *
     * @param message 异常信息
     * @param throwable 引起异常的原因
     * @see Exception#Exception(String, Throwable)
     */
    public JsonParseException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
