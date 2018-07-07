package gnova.core.function;

import gnova.core.annotation.NotNull;
import gnova.core.multimap.MultiMap;

import java.util.Map;

/**
 * MultiMap构造器
 *
 * MultiMap构造器封装了构造一个{@link MultiMap MultiMap}对象的方法
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see ObjectBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface MultiMapBuilder<K, V>
        extends ObjectBuilder<MultiMap<K, V>> {

    /**
     * 构建一个空的MultiMap
     *
     * @return MultiMap对象，不会返回null
     * @see ObjectBuilder#build()
     */
    @Override
    @NotNull
    MultiMap<K, V> build();

    /**
     * 构建一个带有数据的列表MultiMap
     *
     * @param key 键
     * @param value 值
     * @return MultiMap对象，不会返回null
     */
    @NotNull
    default MultiMap<K, V> build(K key, V value) {
        MultiMap<K, V> mm = build();
        mm.put(key, value);
        return mm;
    }

    /**
     * 构建一个带有数据的列表MultiMap
     *
     * @param m MultiMap中的数据
     * @return MultiMap对象，不会返回null
     */
    @NotNull
    default MultiMap<K, V> build(Map<? extends K, ? extends V> m) {
        MultiMap<K, V> mm = build();
        mm.putAll(m);
        return mm;
    }

    /**
     * 构建一个带有数据的列表MultiMap
     *
     * @param mm MultiMap中的数据
     * @return MultiMap对象，不会返回null
     */
    @NotNull
    default MultiMap<K, V> build(MultiMap<? extends K, ? extends V> mm) {
        MultiMap<K, V> nmm = build();
        nmm.putAll(mm);
        return nmm;
    }

}
