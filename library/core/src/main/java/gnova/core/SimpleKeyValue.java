package gnova.core;

import gnova.core.annotation.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * 一个简单的键值对的实现
 *
 * @see KeyValue
 * @author birderyu
 * @version 1.0.0
 */
public class SimpleKeyValue
        implements KeyValue, Serializable, Cloneable {

    /**
     * 键，不会为null
     */
    @NotNull
    private final String key;

    /**
     * 值
     */
    private final Object value;

    /**
     * 构建一个简单的键值对
     *
     * @param key 键，不允许为null
     * @param value 值
     */
    public SimpleKeyValue(@NotNull String key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取键
     *
     * @return 键，不会为null
     * @see KeyValue#getKey()
     */
    @Override
    @NotNull
    public String getKey() {
        return key;
    }

    /**
     * 获取值
     *
     * @param <T> 值的类型
     * @return 值，可以为null
     * @see KeyValue#getValue()
     */
    @Override
    public <T> T getValue() {
        return (T) value;
    }

    /**
     * 判断两个对象是否相等
     *
     * 注意：该方法只会比较键，不会比较值
     *
     * @param o 对象
     * @return 若对象相等，则返回true，否则返回false
     * @see KeyValue#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o instanceof KeyValue) return false;
        return Objects.equals(key, ((KeyValue) o).getKey());
    }

    /**
     * 获取当前对象的散列值
     *
     * 注意：该方法只会获取键的散列值，不会获取值
     *
     * @return 散列值
     * @see KeyValue#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    /**
     * 转换为字符串
     *
     * @return 字符串
     * @see Object#toString()
     */
    @Override
    @NotNull
    public String toString() {
        return "[key: " + key + ", value: " + String.valueOf(value) + "]";
    }

    /**
     * 克隆当前对象
     *
     * @return 键值对
     * @see Object#clone()
     */
    @Override
    public SimpleKeyValue clone() {
        return new SimpleKeyValue(key, value);
    }
}
