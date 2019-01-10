package gnova.core;

import java.util.Objects;

/**
 * 主机和端口
 */
public class HostAndPort {

    /**
     * 主机的地址
     * 如：127.0.0.1
     */
    private String host;

    /**
     * 端口号
     * 如：8080
     */
    private int port;

    /**
     * 主机名
     */
    private String name;

    /**
     * 构造一个主机和端口对象
     *
     * @param host 主机
     * @param port 端口
     */
    public HostAndPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 构造一个主机和端口对象
     * @param host 主机
     * @param port 端口
     * @param name 名称
     */
    public HostAndPort(String host, int port, String name) {
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostAndPort that = (HostAndPort) o;
        return port == that.port &&
                Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public String toString() {
        return host + ':' + port;
    }
}
