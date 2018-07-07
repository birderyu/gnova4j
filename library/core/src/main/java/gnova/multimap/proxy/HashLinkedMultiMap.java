package gnova.multimap.proxy;

import gnova.annotation.NotNull;

import java.util.LinkedList;

/**
 * 使用HashMap和LinkedList实现的MultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class HashLinkedMultiMap<K, V>
        extends HashMultiMapProxy<K, V> {

    /**
     * 构建一个使用HashMap和LinkedList实现的MultiMap对象
     */
    public HashLinkedMultiMap() {
        super();
    }

    /**
     * 构建一个使用HashMap和LinkedList实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     */
    public HashLinkedMultiMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 构建一个使用HashMap和LinkedList实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     * @param loadFactor 加载阈值
     */
    public HashLinkedMultiMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(new LinkedList<>());
    }

}
