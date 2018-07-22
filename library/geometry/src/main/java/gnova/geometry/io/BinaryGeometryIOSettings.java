package gnova.geometry.io;

import gnova.core.Bytes;

public interface BinaryGeometryIOSettings {

    /**
     * 获取当前二进制对象的字节序
     *
     * <p>值有以下两种：
     * <br>{@link Bytes#BIG_ENDIAN 大端字节序}，
     * <br>{@link Bytes#LITTLE_ENDIAN 小端字节序}
     *
     * @return 字节序代表的码值，分别为{@link Bytes#BIG_ENDIAN 大端字节序}和{@link Bytes#LITTLE_ENDIAN 小端字节序}，
     * 默认为{@link Bytes#NETWORK_BYTE_ORDER 网络字节序}，即{@link Bytes#BIG_ENDIAN 大端字节序}。
     * @see gnova.core.Bytes
     */
    default int getEndian() {
        return Bytes.NETWORK_BYTE_ORDER;
    }

}
