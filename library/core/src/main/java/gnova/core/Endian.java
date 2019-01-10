package gnova.core;

/**
 * 字节顺序
 *
 * @author birderyu
 * @version 1.0.0
 */
public enum Endian {

    /**
     * 大端字节序
     */
    BIG_ENDIAN(0),

    /**
     * 小端字节序
     */
    LITTLE_ENDIAN(1);

    /**
     * 字节顺序的值
     */
    private int value;

    /**
     * 构造一个字节顺序
     *
     * @param value 字节顺序的值
     */
    Endian(int value) {
        this.value = value;
    }

    /**
     * 获取字节顺序的值
     *
     * @return 字节顺序的值
     */
    public int getValue() {
        return value;
    }

}
