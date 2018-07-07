package gnova.config;
import gnova.function.Getter;

import java.io.InputStream;

public class ConfigModule
        implements Getter {

    private final Class<?> clazz;

    private final InputStream inputStream;

    private final String node;

    public ConfigModule(Class<?> clazz,
                        InputStream inputStream,
                        String node) {
        this.clazz = clazz;
        this.inputStream = inputStream;
        this.node = node;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getNode() {
        return node;
    }

    @Override
    public <T> T getValue(String key) {
        if ("clazz".equalsIgnoreCase(key)) {
            return (T) clazz;
        } else if ("inputStream".equalsIgnoreCase(key)) {
            return (T) inputStream;
        }  else if ("node".equalsIgnoreCase(key)) {
            return (T) node;
        }
        return null;
    }
}
