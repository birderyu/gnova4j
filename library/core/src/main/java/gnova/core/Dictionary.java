package gnova.core;

import gnova.core.annotation.NotNull;

/**
 * 字典结构
 *
 * <p>字典其实是键值对的集合，其键是字符串类型，值可以是任意类型
 *
 * @see Iterable
 * @see KeyValue
 * @author birderyu
 * @version 1.0.0
 */
public interface Dictionary
        extends Iterable<KeyValue> {

    /**
     * 获取键值对的数量
     *
     * @return 键值对的数量
     */
    int size();

    /**
     * 是否为空
     *
     * @return 若为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 判断是否包含该键
     *
     * @param key 键，不允许为null
     * @return 若包含该键，则返回true，否则返回false
     */
    boolean contains(@NotNull String key);

    /**
     * 获取一个键值对
     *
     * @param key 键，不允许为null
     * @return 若包含该键值对，则返回该键值对，否则返回null
     *          注意，若包含该键值对，但值为null，此时也会返回该键值对，不会返回null
     * @see KeyValue
     */
    KeyValue get(@NotNull String key);

}
