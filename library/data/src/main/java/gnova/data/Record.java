package gnova.data;

import gnova.core.annotation.NotNull;
import gnova.core.KeyValue;
import gnova.core.function.Setter;

/**
 * 记录
 */
public interface Record
        extends ReadOnlyRecord, Setter, Iterable<KeyValue> {

    /**
     * 是否是只读的
     *
     * @return 若是只读的，则返回true，否则返回false
     */
    boolean isReadOnly();

    /**
     * 添加一个键值对
     *
     * @param key 键，不允许为null
     * @param value 值，可以为null，视具体的实现而定
     * @param <T> 值的类型
     * @return 该记录本身
     */
    @NotNull
    <T> Record append(@NotNull String key, T value);

    /**
     * 添加一个键值对
     *
     * @param keyValue 键值对，不允许为null
     * @return 该记录本身
     */
    @NotNull
    Record append(@NotNull KeyValue keyValue);

    /**
     * 移除一个键值对
     *
     * @param key 键，不允许为null
     * @return 该记录本身
     */
    @NotNull
    Record remove(@NotNull String key);

    /**
     * 清空所有键值对
     *
     * @return 该记录本身
     */
    @NotNull
    Record clear();

    @Override
    @NotNull
    Record clone();

    @Override
    @NotNull
    default Record toWritable() {
        return this;
    }
}
