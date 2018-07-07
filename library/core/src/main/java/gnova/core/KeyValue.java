package gnova.core;

import gnova.core.annotation.NotNull;

import java.util.Objects;

/**
 * 键值对
 *
 * @author birderyu
 * @version 1.0.0
 */
public interface KeyValue extends Comparable<KeyValue> {

    /**
     * 获取键
     *
     * @return 键，不会为null
     */
    @NotNull
    String getKey();

    /**
     * 获取值
     *
     * @param <T> 值的类型
     * @return 值，可以为null
     */
    <T> T getValue();

    /**
     * 判断键值对的键和值是否都相等
     *
     * @param kv 另一个键值对，不允许为null
     * @return 若两个键值对的键和值都相等，则返回true，否则返回false
     */
    default boolean deepEquals(@NotNull KeyValue kv) {
        return Objects.equals(getKey(), kv.getKey()) &&
                Objects.equals(getValue(), kv.getValue());
    }

    /**
     * 获取键和值联合后的散列值
     *
     * @return 散列值
     */
    default int deepHashCode() {
        return Objects.hash(getKey(), getValue());
    }

    /**
     * 判断两个对象是否相等
     *
     * 注意：该方法只会比较键，不会比较值
     *
     * @param o 对象
     * @return 若对象相等，则返回true，否则返回false
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object o);

    /**
     * 获取当前对象的散列值
     *
     * 注意：该方法只会获取键的散列值，不会获取值
     *
     * @return 散列值
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * 比较当前键值对与另一个键值对
     *
     * 注意：该方法只会比较键，不会比较值
     *
     * @param kv 另一个键值对，不允许为null
     * @return 若当前键值对大于另一个键值对，则返回值大于0
     *          若当前键值对等于另一个键值对，则返回值等于0
     *          若当前键值对小于另一个键值对，则返回值小于0
     * @see Comparable#compareTo(Object)
     */
    @Override
    default int compareTo(@NotNull KeyValue kv) {
        return getKey().compareTo(kv.getKey());
    }

}
