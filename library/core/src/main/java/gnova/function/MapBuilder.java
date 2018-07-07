package gnova.function;

import gnova.annotation.NotNull;

import java.util.Map;

/**
 * Map构造器
 *
 * Map构造器封装了构造一个{@link Map Map}对象的方法
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see ObjectBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface MapBuilder<K, V>
        extends ObjectBuilder<Map<K, V>> {

    /**
     * 构建一个空的Map
     *
     * @return Map对象，不会返回null
     * @see ObjectBuilder#build()
     */
    @Override
    @NotNull
    Map<K, V> build();

    /**
     * 构建一个带有数据的Map
     *
     * @param key 键
     * @param value 值
     * @return Map对象，不会返回null
     */
    @NotNull
    default Map<K, V> build(K key, V value) {
        Map<K, V> m = build();
        m.put(key, value);
        return m;
    }

    /**
     * 构建一个带有数据的Map
     *
     * @param m Map中的数据
     * @return Map对象，不会返回null
     */
    @NotNull
    default Map<K, V> build(Map<? extends K, ? extends V> m) {
        Map<K, V> nm = build();
        nm.putAll(m);
        return nm;
    }

}
