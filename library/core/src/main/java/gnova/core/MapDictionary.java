package gnova.core;

import gnova.core.annotation.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 使用Map实现的字典对象
 *
 * @see Map
 * @see Dictionary
 * @author birderyu
 * @version 1.0.0
 */
public class MapDictionary
        implements Dictionary {

    /**
     * Map对象，不会为null
     */
    @NotNull
    private final Map<String, Object> map;

    /**
     * 构建一个使用Map实现的字典对象
     *
     * @param map Map对象，不允许为null
     */
    public MapDictionary(@NotNull Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 获取键值对的数量
     *
     * @return 键值对的数量
     * @see Dictionary#size()
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * 是否为空
     *
     * @return 若为空，则返回true，否则返回false
     * @see Dictionary#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
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
        return map.containsKey(key);
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
        if (!map.containsKey(key)) {
            return null;
        }
        return new SimpleKeyValue(key, map.get(key));
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
        return new Iter(map.entrySet().iterator());
    }

    /**
     * 迭代器的内部实现
     */
    private static class Iter implements ReadOnlyIterator<KeyValue> {

        /**
         * Map Entry的迭代器，不会为null
         */
        @NotNull
        private final Iterator<Map.Entry<String, Object>> entrys;

        /**
         * 构建一个迭代器
         *
         * @param entrys Map Entry的迭代器，不允许为null
         */
        public Iter(@NotNull Iterator<Map.Entry<String, Object>> entrys) {
            this.entrys = entrys;
        }

        /**
         * 判断是否还有下一个元素
         *
         * @return 若还有下一个元素，则返回true，否则返回false
         * @see Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return entrys.hasNext();
        }

        /**
         * 返回下一个元素
         *
         * @return 元素对象
         * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
         * @see Iterator#next()
         */
        @Override
        public KeyValue next() throws NoSuchElementException {
            Map.Entry<String, Object> entry = entrys.next();
            if (entry == null) {
                return null;
            }
            return new SimpleKeyValue(entry.getKey(), entry.getValue());
        }
    }

}
