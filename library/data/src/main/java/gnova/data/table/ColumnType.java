package gnova.data.table;

/**
 * 列的类型
 */
public enum ColumnType {

    /**
     * 布尔类型
     */
    Bool(boolean.class),

    /**
     * 字节类型
     */
    Byte(byte.class),

    /**
     * 字符类型
     */
    Char(char.class),

    /**
     * 16位整型
     */
    Int16(short.class),

    /**
     * 32位整型
     */
    Int32(int.class),

    /**
     * 64位整型
     */
    Int64(long.class),

    /**
     * 浮点型
     */
    Float(float.class),

    /**
     * 双精度浮点型
     */
    Double(double.class),

    /**
     * 大整型
     */
    BigInteger(java.math.BigInteger.class),

    /**
     * 高精度浮点型
     */
    BigDecimal(java.math.BigDecimal.class),

    /**
     * 二进制类型
     */
    Binary(java.nio.ByteBuffer.class),

    /**
     * 字符串类型
     */
    String(java.lang.String.class),

    /**
     * 列表类型
     */
    List(java.util.List.class),

    /**
     * 集合类型
     */
    Set(java.util.Set.class),

    /**
     * 几何类型
     */
    Geometry(gnova.geometry.model.Geometry.class),

    /**
     * 对象类型
     */
    Object(java.lang.Object.class);

    private final Class<?> clazz;
    ColumnType(Class<?> clazz) {
        this.clazz = clazz;
    }
    public Class<?> getClazz() {
        return clazz;
    }

}
