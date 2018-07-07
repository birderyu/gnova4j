package gnova.function;

import gnova.annotation.NotNull;

import java.util.Map;
import java.util.NavigableMap;

/**
 * NavigableMap构造器
 *
 * NavigableMap构造器封装了构造一个{@link NavigableMap NavigableMap}对象的方法
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see SortedMapBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface NavigableMapBuilder<K, V>
        extends SortedMapBuilder<K, V> {

    /**
     * 构建一个空的NavigableMap
     *
     * @return NavigableMap对象，不会返回null
     * @see SortedMapBuilder#build()
     */
    @Override
    @NotNull
    NavigableMap<K, V> build();

    /**
     * 构建一个带有数据的NavigableMap
     *
     * @param key 键
     * @param value 值
     * @return NavigableMap对象，不会返回null
     * @see SortedMapBuilder#build(Object, Object)
     */
    @Override
    @NotNull
    default NavigableMap<K, V> build(K key, V value) {
        NavigableMap<K, V> nm = build();
        nm.put(key, value);
        return nm;
    }

    /**
     * 构建一个带有数据的NavigableMap
     *
     * @param m Map中的数据
     * @return NavigableMap对象，不会返回null
     * @see SortedMapBuilder#build(Map)
     */
    @Override
    @NotNull
    default NavigableMap<K, V> build(Map<? extends K, ? extends V> m) {
        NavigableMap<K, V> nm = build();
        nm.putAll(m);
        return nm;
    }

}
