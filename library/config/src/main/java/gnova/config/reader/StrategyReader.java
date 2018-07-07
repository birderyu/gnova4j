package gnova.config.reader;

import gnova.config.ConfigException;
import gnova.config.ConfigModule;

public interface StrategyReader {

    <T> T read(ConfigModule module) throws ConfigException;

}
