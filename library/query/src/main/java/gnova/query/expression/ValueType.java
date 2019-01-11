package gnova.query.expression;

/**
 * 值表达式的类型
 */
public enum ValueType {

    /**
     * 空值类型
     */
    Null((byte) 1),

    /**
     * 布尔值类型
     */
    Boolean((byte) 2),

    /**
     * 32位整型值类型
     */
    Int32((byte) 3),

    /**
     * 64位整型值类型
     */
    Int64((byte) 4),

    /**
     * 双精度浮点型值类型
     */
    Double((byte) 5),

    /**
     * 字符串值类型
     */
    String((byte) 6),

    /**
     * 字节串值类型
     */
    Bytes((byte) 7),

    /**
     * 日期值类型
     */
    Date((byte) 8),

    /**
     * 时间戳值类型
     */
    Timestamp((byte) 9),

    /**
     * 几何区域值类型
     */
    Geometry((byte) 10),

    /**
     * 列表值类型
     */
    List((byte) 11),

    /**
     * 键值类型
     */
    Key((byte) 12),

    /**
     * 占位符类型
     */
    Placeholder((byte) 13);

    private final byte type;

    ValueType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
