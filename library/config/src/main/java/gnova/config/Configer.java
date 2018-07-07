package gnova.config;

import java.io.InputStream;

public interface Configer {

    /**
     * 加载一个配置文件，使之成为一个对象
     *
     * @param node 配置文件的一个节点，若为null，则表示根节点
     * @param is 配置文件的输入流
     * @param strategy 配置文件的类型
     * @param clazz 配置文件的对象类型
     * @param <T>
     * @return
     * @throws ConfigException
     */
    <T> T read(String node, InputStream is,
               Strategy strategy,
               Class<? extends T> clazz)
            throws ConfigException;

    /*
    <T> void writeField(String node, OutputStream os,
                   Strategy strategy,
                   T config, Class<T> clazz)
            throws ConfigException;
            */

}
