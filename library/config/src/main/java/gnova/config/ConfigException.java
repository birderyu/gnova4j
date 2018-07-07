package gnova.config;

public class ConfigException
        extends Exception {

    /**
     * 构造器
     */
    public ConfigException() {
        super();
    }

    /**
     * 构造器
     * @param message 异常信息
     */
    public ConfigException(String message) {
        super(message);
    }

    /**
     * 构造器
     * @param throwable 引起异常的原因
     */
    public ConfigException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构造器
     * @param message 异常信息
     * @param throwable 引起异常的原因
     */
    public ConfigException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
