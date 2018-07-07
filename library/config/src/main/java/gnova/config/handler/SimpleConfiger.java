package gnova.config.handler;

import gnova.config.ConfigException;
import gnova.config.ConfigModule;
import gnova.config.Configer;
import gnova.config.Strategy;
import gnova.config.reader.StrategyReader;
import gnova.config.reader.json.JsonStrategyReader;
import gnova.config.reader.xml.XmlStrategyReader;
import gnova.config.reader.yaml.YamlStrategyReader;

import java.io.InputStream;

public class SimpleConfiger implements Configer {

    @Override
    public <T> T read(String node, InputStream is, Strategy strategy, Class<? extends T> clazz)
            throws ConfigException {

        ConfigModule module = new ConfigModule(clazz, is, node);
        StrategyReader reader = null;
        switch (strategy) {
            case Xml:
                reader = new XmlStrategyReader();
                break;
            case Json:
                reader = new JsonStrategyReader();
                break;
            case Yaml:
                reader = new YamlStrategyReader();
                break;
        }
        return reader.read(module);
    }

}
