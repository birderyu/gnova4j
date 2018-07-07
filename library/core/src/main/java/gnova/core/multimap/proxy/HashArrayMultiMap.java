package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;

import java.util.ArrayList;

/**
 * 使用HashMap和ArrayList实现的MultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class HashArrayMultiMap<K, V>
        extends HashMultiMapProxy<K, V> {

    /**
     * 构建一个使用HashMap和ArrayList实现的MultiMap对象
     */
    public HashArrayMultiMap() {
        super();
    }

    /**
     * 构建一个使用HashMap和ArrayList实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     */
    public HashArrayMultiMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 构建一个使用HashMap和ArrayList实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     * @param loadFactor 加载阈值
     */
    public HashArrayMultiMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(new ArrayList<>());
    }

}
