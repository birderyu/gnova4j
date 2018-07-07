package gnova.core;

import gnova.core.annotation.NotNull;

/**
 * 一个简单的字典结构的实现
 *
 * @see Dictionary
 * @author birderyu
 * @version 1.0.0
 */
public class SimpleDictionary
        implements Dictionary {

    /**
     * 键值对的数组
     */
    private KeyValue[] keyValues;

    /**
     * 构建一个简单的字典结构
     *
     * @param keyValues 键值对的数组
     */
    public SimpleDictionary(KeyValue... keyValues) {
        this.keyValues = keyValues;
    }

    /**
     * 获取键值对的数量
     *
     * @return 键值对的数量
     * @see Dictionary#size()
     */
    @Override
    public int size() {
        return keyValues == null ? 0 : keyValues.length;
    }

    /**
     * 是否为空
     *
     * @return 若为空，则返回true，否则返回false
     * @see Dictionary#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return keyValues == null ? true : keyValues.length == 0;
    }

    /**
     * 判断是否包含该键
     *
     * @param key 键，不允许为null
     * @return 若包含该键，则返回true，否则返回false
     * @see Dictionary#contains(String)
     */
    @Override
    public boolean contains(String key) {
        if (keyValues == null) {
            return false;
        }
        for (KeyValue keyValue : keyValues) {
            if (keyValue.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取一个键值对
     *
     * @param key 键，不允许为null
     * @return 若包含该键值对，则返回该键值对，否则返回null
     *          注意，若包含该键值对，但值为null，此时也会返回该键值对，不会返回null
     * @see Dictionary#get(String)
     * @see KeyValue
     */
    @Override
    public KeyValue get(String key) {
        if (keyValues == null) {
            return null;
        }
        for (KeyValue keyValue : keyValues) {
            if (keyValue.getKey().equals(key)) {
                return keyValue;
            }
        }
        return null;
    }

    /**
     * 获取只读的迭代器
     *
     * @return 只读的迭代器，不会返回null
     * @see ReadOnlyIterable#iterator()
     * @see ReadOnlyIterator
     */
    @Override
    @NotNull
    public ReadOnlyIterator<KeyValue> iterator() {
        return keyValues == null ?
                new EmptyIterator<>() :
                new ReadOnlyIteratorProxy<>(new ArrayIterator<>(keyValues));
    }
}
