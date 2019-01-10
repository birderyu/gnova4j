package gnova.geometry.io;

import gnova.core.Endian;

public interface BinaryGeometryIOSettings {

    /**
     * 获取当前二进制对象的字节序
     *
     * <p>值有以下两种：
     * <br>{@link Endian#BIG_ENDIAN 大端字节序}，
     * <br>{@link Endian#LITTLE_ENDIAN 小端字节序}
     *
     * @return 字节序代表的码值，分别为{@link Endian#BIG_ENDIAN 大端字节序}和{@link Endian#LITTLE_ENDIAN 小端字节序}，
     * 默认为{@link Endian#BIG_ENDIAN 大端字节序}。
     * @see gnova.core.Bytes
     */
    default Endian getEndian() {
        return Endian.BIG_ENDIAN;
    }

}
