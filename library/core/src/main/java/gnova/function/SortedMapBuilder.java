package gnova.function;

import gnova.annotation.NotNull;

import java.util.Map;
import java.util.SortedMap;

/**
 * SortedMap构造器
 *
 * SortedMap构造器封装了构造一个{@link SortedMap SortedMap}对象的方法
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see MapBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface SortedMapBuilder<K, V>
        extends MapBuilder<K, V> {

    /**
     * 构建一个空的SortedMap
     *
     * @return SortedMap对象，不会返回null
     * @see MapBuilder#build()
     */
    @Override
    @NotNull
    SortedMap<K, V> build();

    /**
     * 构建一个带有数据的SortedMap
     *
     * @param key 键
     * @param value 值
     * @return SortedMap对象，不会返回null
     * @see MapBuilder#build(Object, Object)
     */
    @Override
    @NotNull
    default SortedMap<K, V> build(K key, V value) {
        SortedMap<K, V> sm = build();
        sm.put(key, value);
        return sm;
    }

    /**
     * 构建一个带有数据的SortedMap
     *
     * @param m Map中的数据
     * @return SortedMap对象，不会返回null
     * @see MapBuilder#build(Map)
     */
    @Override
    @NotNull
    default SortedMap<K, V> build(Map<? extends K, ? extends V> m) {
        SortedMap<K, V> sm = build();
        sm.putAll(m);
        return sm;
    }

}
